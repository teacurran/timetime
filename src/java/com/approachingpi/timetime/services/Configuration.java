package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.approachingpi.timetime.util.StringUtils;
import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.solder.resourceLoader.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 1/7/2012
 *
 * @author T. Curran
 */
@Named
@ApplicationScoped
public class Configuration
		implements Serializable {

	private static final long serialVersionUID = -721835784780918971L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	@Inject
	@Resource("settings.mode.properties")
	Properties modeProperties;

	@Inject
	@Resource("settings.properties")
	Properties siteProperties;

	// @Inject
	// @Resource( "messages" )
	// Properties messages;

	@Inject
	MessageFactory messageFactory;

	public Configuration() {

	}

	public String getSetting(
			final String key) {

		if (this.modeProperties.containsKey(key)) {
			return this.modeProperties.getProperty(key);
		}
		if (this.siteProperties.containsKey(key)) {
			return this.siteProperties.getProperty(key);
		}
		return null;
	}

	public String getSetting(
			final String key,
			final String defaultValue) {

		final String returnValue = this.getSetting(key);
		if (StringUtils.isEmpty(returnValue)) {
			return defaultValue;
		}
		return returnValue;
	}

	public int getSettingInt(
			final String key) {

		final String resultString = this.getSetting(key);
		int resultInt = 0;
		try {
			resultInt = Integer.parseInt(resultString);
		} catch (final NumberFormatException e) {
			// do nothing
			LOGGER.warn("Attempt to cast setting as int failed. key:'{}'", key);
		}
		return resultInt;
	}

	public int getSettingInt(
			final String key,
			final int defaultValue) {

		final String resultString = this.getSetting(key);
		int resultInt = 0;
		try {
			resultInt = Integer.parseInt(resultString);
		} catch (final NumberFormatException e) {
			resultInt = defaultValue;
		}
		return resultInt;
	}

	public boolean getSettingBool(final String key) {
		return getSettingBool(key, false);
	}

	public boolean getSettingBool(
			final String key,
			final boolean defaultValue) {

		String resultString = getSetting(key);
		boolean resultBool;
		try {
			resultBool = Boolean.parseBoolean(resultString);
		} catch (NumberFormatException e) {
			resultBool = defaultValue;
		}
		return resultBool;
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @param params
	 * @return
	 */
	public String getMessage(
			final String key,
			final String defaultValue,
			final Object... params) {

		final BundleTemplateMessage bundleMessage =
				this.messageFactory.info(new BundleKey("i18n", key), params);
		bundleMessage.defaults(defaultValue);

		return bundleMessage.build().getText();
	}

	public String getMessage(
			final String key) {

		return this.getMessage(key, null);
	}

}