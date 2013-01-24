package com.approachingpi.timetime.services.mail;


import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.mail.Session;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import com.approachingpi.timetime.services.mail.MailConfig;
import org.jboss.seam.mail.util.MailUtility;


/**
 * Date: 1/23/13
 *
 * @author T. Curran
 */
public class MailSessionProducer {

	@Inject
	@ApplicationInstance
	private MailConfig mailConfig;

	@Produces
	@ApplicationInstance
	public Session getMailSession() {

		return MailUtility.createSession(this.mailConfig);
	}
}
