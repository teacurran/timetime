package com.approachingpi.timetime.security;

import com.approachingpi.timetime.util.PasswordHash;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.hash.ConfigurableHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.google.inject.Inject;


/**
 * Date: 3/21/13
 *
 * @author T. Curran
 */
public class PasswordMatchService extends DefaultPasswordService {

	public PasswordMatchService() {
		final ConfigurableHashService hashService = (ConfigurableHashService) this.getHashService();
		hashService.setHashAlgorithmName(PasswordHash.ALGORITHM);
		hashService.setHashIterations(PasswordHash.ITERATIONS);
	}
}
