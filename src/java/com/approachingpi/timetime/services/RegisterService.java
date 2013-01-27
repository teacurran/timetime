package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.security.Identity;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.approachingpi.timetime.data.model.Account;
import com.approachingpi.timetime.data.model.Company;
import org.picketlink.idm.api.Group;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.PersistenceManager;
import org.picketlink.idm.api.RelationshipManager;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.IdentityException;
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
	IdentitySession identitySession;

	String inputFullName;
	String inputEmail;
	String inputCompany;
	String inputPassword;

	public void registerAccount() {

		PersistenceManager pm = identitySession.getPersistenceManager();

		try {
			User user = pm.createUser(inputEmail);

			Group group = null;
			Collection<Group> groups = pm.findGroup("user");
			if (groups != null && groups.size() > 0) {
				for (Group thisGroup : groups) {
					group = thisGroup;
					break;
				}
			} else {
				group = pm.createGroup("user", "user");
			}

			if (group != null) {
				RelationshipManager rm = identitySession.getRelationshipManager();
				rm.associateUser(group, user);
			}


		} catch (IdentityException ie) {
			LOGGER.error("error creating account", ie);

		}

//		Account account =new Account();
//		account.setFullName(inputFullName);
//
//		Company company = new Company(inputCompany);
//		em.persist(company);
//		account.setCompany(company);
//
//		accountService.setPassword(account, inputPassword);
//
//		em.persist(account);

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
