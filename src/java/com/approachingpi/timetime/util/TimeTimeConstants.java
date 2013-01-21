package com.approachingpi.timetime.util;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
public interface TimeTimeConstants {
	String REGEX_BOOLEAN = "(true|false)";
	String REGEX_STRING = "[\\p{L}\\p{Z}\\p{S}\\p{N}\\p{P}]{0,}";
	String REGEX_USERNAME = "[\\p{L}\\p{Z}\\p{S}\\p{N}\\p{P}]{0,}";
	String REGEX_CHARACTER = "[\\p{L}\\p{Z}\\p{S}\\p{N}\\p{P}]{1}";
	String REGEX_NUMBER = "[+-]{0,1}[\\p{N}]{1,}";
	String REGEX_NUMBER_NN = "[+]{0,1}[\\p{N}]{1,}";
	String REGEX_DATE = "\\d{4}\\-\\d{2}-\\d{2}";
	String FORMAT_DATE = "yyyy-MM-dd";
	String REGEX_DATE_TIME = "\\d{4}\\-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
	String FORMAT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
	String REGEX_TOKEN = "[a-fA-F0-9\\-]{1,255}";
	String REGEX_EMAIL = "([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)";
	String REGEX_PHONE_NUMBER = "(\\d{10})?";
	String REGEX_PHONE_EXTENSION = "(\\d{1,5})?";
	String REGEX_ZIP_CODE = "\\d{5}(\\-\\d{4})?";
	String REGEXP_USERNAME = "^[A-Za-z0-9_]+$";
	String REGEXP_PASSWORD = "^[A-Za-z0-9_\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\|\\~\\`\\+\\=\\[\\]\\{\\}\\|\\\\]+$";
}
