package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.approachingpi.timetime.data.model.Account;


/**
 * Date: 8/2/12
 *
 * @author T. Curran
 */
@Named
@SessionScoped
public class SessionService implements Serializable {

	private static final long serialVersionUID = 2525045763445416538L;

	protected String accessCode = null;

	protected Account account;

	public String getAccessCode() {
		if (accessCode == null) {
			accessCode = UUID.randomUUID().toString();
		}
		return accessCode;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
