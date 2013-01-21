package com.approachingpi.timetime.services;

import javax.inject.Named;

import com.approachingpi.timetime.data.model.Account;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@Named
public class RegisterService {

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
