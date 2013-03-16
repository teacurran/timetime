package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.security.Identity;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.persistence.TypedQuery;

import com.approachingpi.timetime.data.model.Account;
import com.approachingpi.timetime.data.model.Company;
import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@Named
@SessionScoped
public class RegisterService extends BaseService implements Serializable {

	private static final long serialVersionUID = -5441655456142315364L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);

	@Inject
	AccountService accountService;

	@Inject
	transient Subject subject;

	String inputFullName;
	String inputEmail;
	String inputCompany;
	String inputPassword;

	public void registerAccount() {

		TypedQuery<Account> emailCheckQuery = em.createQuery("SELECT a FROM Account a WHERE a.email=:email", Account.class);
		emailCheckQuery.setParameter("email", inputEmail);
		List<Account> emailCheckResults = emailCheckQuery.getResultList();
		if (emailCheckResults != null && emailCheckResults.size() > 0) {
			// TODO: send an error to the user
			return;
		}

		Account account = new Account();
		account.setEmail(inputEmail);
		account.setUsername(inputEmail);
		accountService.setPassword(account, inputPassword);

		Company company = new Company();
		company.setName(inputCompany);
		em.persist(company);

		account.setCompany(company);
		em.persist(account);

		// Authenticate
		subject.login( new UsernamePasswordToken(inputEmail, inputPassword));
	}

	public String getInputFullName() {
		return inputFullName;
	}

	public void setInputFullName(String inputFullName) {
		this.inputFullName = inputFullName;
	}

	public String getInputEmail() {
		return inputEmail;
	}

	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}

	public String getInputCompany() {
		return inputCompany;
	}

	public void setInputCompany(String inputCompany) {
		this.inputCompany = inputCompany;
	}

	public String getInputPassword() {
		return inputPassword;
	}

	public void setInputPassword(String inputPassword) {
		this.inputPassword = inputPassword;
	}
}
