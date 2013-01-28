package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.security.Identity;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

	String inputFullName;
	String inputEmail;
	String inputCompany;
	String inputPassword;

	public void registerAccount() {


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
