package com.approachingpi.timetime.security;

import com.approachingpi.timetime.util.PasswordHash;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.hash.ConfigurableHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;


/**
 * Date: 3/21/13
 *
 * @author T. Curran
 */
public class PasswordMatchService implements HashingPasswordService {

	public PasswordMatchService() {
	}

	@Override
	public Hash hashPassword(Object plainText) throws IllegalArgumentException {
		
		//String plainTextString = (String)plainText;

		//HashRequest hq = new HashRequest.Builder().setSource(plainTextString).build();
		
		//PasswordHash ph = new PasswordHash(plainTextString);
		
		// AUTO GENERATED IMPLEMENTAITON METHOD
		
		// TODO: implement
		return null;
	}

	@Override
	public boolean passwordsMatch(Object plaintext, Hash savedPasswordHash) {

		// TODO: implement

		// AUTO GENERATED IMPLEMENTAITON METHOD
		return false;
	}

	@Override
	public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {

		// TODO: implement

		// AUTO GENERATED IMPLEMENTAITON METHOD
		return null;
	}

	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {

				// TODO: implement

		// AUTO GENERATED IMPLEMENTAITON METHOD
		return false;
	}
}
