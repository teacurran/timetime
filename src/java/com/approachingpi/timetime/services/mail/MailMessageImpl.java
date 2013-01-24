package com.approachingpi.timetime.services.mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.mail.Session;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import org.jboss.seam.mail.api.MailMessage;
import org.jboss.seam.mail.core.EmailMessage;
import org.jboss.seam.mail.core.SendFailedException;
import org.jboss.seam.mail.templating.TemplateProvider;
import org.jboss.seam.mail.util.MailUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cody Lerum
 */
@ApplicationInstance
public class MailMessageImpl
		extends org.jboss.seam.mail.core.MailMessageImpl
		implements MailMessage, Serializable {

	private static final long serialVersionUID = 7941381802280982673L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MailMessageImpl.class);

	private TemplateProvider subjectTemplate;
	private TemplateProvider textTemplate;
	private TemplateProvider htmlTemplate;
	private final Map<String, Object> templateContext = new HashMap<String, Object>();
	private boolean templatesMerged;

	@Inject
	@ApplicationInstance
	private Instance<Session> session;

	public MailMessageImpl() {

		this.setEmailMessage(new EmailMessage());
	}

	@Override
	public EmailMessage send(
			final Session session)
			throws SendFailedException {

		if (!this.templatesMerged) {
			this.mergeTemplates();
		}

		try {
			MailUtility.send(this.getEmailMessage(), session);
		} catch (SendFailedException sfe) {
			LOGGER.error("unable to send mail", sfe);
			throw sfe;
		}

		return this.getEmailMessage();
	}

	@Override
	public EmailMessage send()
			throws SendFailedException {

		return this.send(this.session.get());
	}
}