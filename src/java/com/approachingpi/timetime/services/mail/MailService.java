package com.approachingpi.timetime.services.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import com.approachingpi.timetime.services.BaseService;
import com.approachingpi.timetime.services.Configuration;
import org.jboss.seam.mail.api.MailMessage;
import org.jboss.seam.mail.core.BasicEmailContact;
import org.jboss.seam.mail.core.EmailContact;
import org.jboss.seam.mail.core.enumerations.MessagePriority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 1/23/13
 *
 * @author T. Curran
 */
@Named
public class MailService extends BaseService implements Serializable {

	private static final long serialVersionUID = 3572183154201772538L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Inject
	@ApplicationInstance
	private MailMessage mailMessage;

	@Inject
	@ApplicationInstance
	private Instance<Session> session;

	@Inject
	private Configuration configuration;

	private Hashtable<String, String> replacements = new Hashtable<String, String>();

	private String templateContent;
	private String textTemplateContent;

	public void setTemplate(
			final String template) {

		final InputStream is = this.getClass().getResourceAsStream("/emails/" + template);
		try {
			this.templateContent = this.convertStreamToString(is);
		} catch (final IOException e) {
		}
	}

	public void setTextTemplate(
			final String template) {

		final InputStream is = this.getClass().getResourceAsStream("/emails/" + template);
		try {
			this.textTemplateContent = this.convertStreamToString(is);
		} catch (final IOException e) {
		}
	}

	public void setReplacement(
			final String key,
			String value) {

		if (key == null) {
			return;
		}
		if (this.replacements == null) {
			this.replacements = new Hashtable<String, String>();
		}
		if (value == null) {
			value = "";
		}
		this.replacements.put(key, value);
	}

	public void sendEmail(
			final String toName,
			final String toEmail,
			final String subject) {

		BasicEmailContact contact = new BasicEmailContact(toEmail, toName);

		this.sendEmail(contact, subject);
	}

	public void sendEmail(
			final EmailContact to,
			final String subject) {

		LOGGER.debug("sending email to:{}", to.getAddress());

		final Enumeration<String> keys = this.replacements.keys();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			final String value = this.replacements.get(key);
			this.templateContent = this.templateContent.replace(key, value);
			if (this.textTemplateContent != null) {
				this.textTemplateContent = this.textTemplateContent.replace(key, value);
			}
		}

		this.mailMessage.from(this.configuration.getSetting(
				"mail.address.from",
				"noreply@mslifelines.com"));
		this.mailMessage.to(to);
		this.mailMessage.subject(subject);
		if (this.textTemplateContent != null) {
			this.mailMessage.bodyHtmlTextAlt(this.templateContent, this.textTemplateContent);
		} else {
			this.mailMessage.bodyHtml(this.templateContent);
		}
		this.mailMessage.importance(MessagePriority.NORMAL);
		this.mailMessage.send();

	}

	private String convertStreamToString(
			final InputStream is)
			throws IOException {

		if (is == null) {
			return "";
		}
		final Writer writer = new StringWriter();
		final char[] buffer = new char[1024];
		try {
			final Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		return writer.toString();
	}

}
