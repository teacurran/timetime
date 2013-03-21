package com.approachingpi.timetime.security;

import javax.inject.Inject;
import javax.inject.Named;

import com.approachingpi.timetime.data.model.Account;
import com.approachingpi.timetime.services.AccountService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Date: 1/31/13
 *
 * @author T. Curran
 */
@Named
public class TimeTimeAuthorizingRealm extends AuthorizingRealm {

	@Inject
	AccountService accountService;

	private SimpleAccount getAccount(
			final String inPrincipal) {

		Account account = accountService.find(inPrincipal);

		if (account == null) {
			// User does not exist
			return null;
		}

		// Create / initialize authentication / authorization "account"
		final SimpleAccount simpleAccount =
				new SimpleAccount(account.getEmail(), account.getPassword(), this.getName());

		return simpleAccount;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			final PrincipalCollection inPrincipalCollection) {

		return this.getAccount(this.getAvailablePrincipal(inPrincipalCollection).toString());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			final AuthenticationToken inAuthenticationToken)
			throws AuthenticationException {

		final UsernamePasswordToken usernamePasswordToken =
				(UsernamePasswordToken) inAuthenticationToken;


		Account account = accountService.find(usernamePasswordToken.getUsername());
		if (account == null) {
			return null;
		}

		if (!accountService.checkPassword(account, new String(usernamePasswordToken.getPassword()))) {
			throw new IncorrectCredentialsException();
		}

		// Create / initialize authentication / authorization "account"
		final SimpleAccount simpleAccount =
				new SimpleAccount(account.getEmail(),
						account.getPassword(),
						ByteSource.Util.bytes(account.getPasswordSalt()),
						this.getName());

		if (simpleAccount != null) {

			if (simpleAccount.isLocked()) {
				throw new LockedAccountException(
						String.format("Account [%s] is locked.", account));
			}
			if (simpleAccount.isCredentialsExpired()) {
				throw new ExpiredCredentialsException(String.format(
						"The credentials for account [%s] are expired.",
						account));
			}
		}

		return simpleAccount;
	}

}
