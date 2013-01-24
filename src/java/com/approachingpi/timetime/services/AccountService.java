package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;

import com.approachingpi.timetime.data.model.Account;
import com.approachingpi.timetime.data.model.AccountPasswordReset;
import com.approachingpi.timetime.exceptions.ServiceException;
import com.approachingpi.timetime.services.mail.MailService;
import com.approachingpi.timetime.util.PasswordHash;
import com.approachingpi.timetime.util.StringUtils;

/**
 * Date: 1/23/13
 *
 * @author T. Curran
 */
@Named
public class AccountService extends BaseService implements Serializable {

	private static final long serialVersionUID = -4899150565335105759L;

	@Inject
	@New
	private MailService mailService;

	@Inject
	private Configuration configuration;

	public Account find(final String inAccountId) {

		try {
			Long accountId = Long.parseLong(inAccountId);

			return em.find(Account.class, accountId);
		} catch (NumberFormatException nfe) {
			// do nothing, perhaps they passed in a username;
		}

		String usernameNormalized = StringUtils.normalizeUsername(inAccountId);
		TypedQuery<Account> query = em.createNamedQuery(Account.QUERY_BY_USERNAME_NORMALIZED, Account.class);
		query.setParameter("username", usernameNormalized);

		List<Account> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}

		return null;
	}

	public void setPassword(Account inAccount, String inPassword) throws ServiceException {
		if (inAccount == null) {
			return;
		}

		if (inPassword == null || inPassword.length() == 0) {
			throw new IllegalArgumentException("inPassword cannot be empty");
		}

		try {
			PasswordHash ph = new PasswordHash(inPassword);
			inAccount.setPasswordSalt(ph.getSalt());
			inAccount.setPassword(ph.getHash());

		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		} catch (InvalidKeySpecException e) {
			throw new ServiceException(e);
		}
	}

	public void requestPasswordReset(Account inAccount) {

		if (inAccount == null) {
			return;
		}
		if (StringUtils.isEmpty(inAccount.getEmail())) {
			return;
		}

		AccountPasswordReset passwordReset = new AccountPasswordReset(inAccount);
		em.persist(passwordReset);

		String resetPasswordUrl = configuration.getSetting("email.resetpassword.url");
		resetPasswordUrl = resetPasswordUrl.replaceAll("#KEY#", passwordReset.getUuid());

		mailService.setTemplate("reset-password.html");
		mailService.setReplacement("#NAME#", inAccount.getFullName());
		mailService.setReplacement("#RESET_URL#", resetPasswordUrl);

		mailService.sendEmail(
				inAccount.getFullName(),
				inAccount.getEmail(),
				configuration.getMessage(
						"email.passwordreset.subject",
						"TimeTime - password reset"));
	}

	public boolean checkPassword(Account inAccount, String inPassword) throws ServiceException {
		if (inAccount == null || inPassword == null) {
			return false;
		}

		try {
			return PasswordHash.check(inPassword, inAccount.getPasswordSalt(), inAccount.getPassword());
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		} catch (InvalidKeySpecException e) {
			throw new ServiceException(e);
		}
	}

}
