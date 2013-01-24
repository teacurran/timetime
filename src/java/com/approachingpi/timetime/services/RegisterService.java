package com.approachingpi.timetime.services;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.approachingpi.timetime.data.model.Account;
import com.approachingpi.timetime.data.model.Company;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@Named
@SessionScoped
public class RegisterService extends BaseService implements Serializable {

	private static final long serialVersionUID = -5441655456142315364L;

	@Inject
	AccountService accountService;

	String inputFullName;
	String inputEmail;
	String inputCompany;
	String inputPassword;

	public void registerAccount() {

		Account account = new Account();
		account.setFullName(inputFullName);

		Company company = new Company(inputCompany);
		em.persist(company);
		account.setCompany(company);

		accountService.setPassword(account, inputPassword);

		em.persist(account);
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
