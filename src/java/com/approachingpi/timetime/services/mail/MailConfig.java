package com.approachingpi.timetime.services.mail;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import com.approachingpi.timetime.services.Configuration;

/**
 * Date: 1/23/13
 *
 * @author T. Curran
 */
@ApplicationScoped
@ApplicationInstance
public class MailConfig extends org.jboss.seam.mail.core.MailConfig implements Serializable {

	private static final long serialVersionUID = 820027697468871315L;

	@Inject
	Configuration configuration;

	@Override
	public String getServerHost() {

		return this.configuration.getSetting("mail.smtp.host", super.getServerHost());
	}

	@Override
	public Integer getServerPort() {

		return this.configuration.getSettingInt("mail.smtp.port", super.getServerPort());
	}

	@Override
	public boolean isValid() {

		if (this.getServerHost() == null) {
			return false;
		}

		if (this.getServerHost().trim().equals("")) {
			return false;
		}

		if (this.getServerPort() == 0) {
			return false;
		}

		return true;
	}
}
