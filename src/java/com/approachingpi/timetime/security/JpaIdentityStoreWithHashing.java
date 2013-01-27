package com.approachingpi.timetime.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.approachingpi.timetime.data.model.IdentityObjectAttribute;
import com.approachingpi.timetime.data.model.IdentityObjectCredential;
import org.apache.commons.codec.binary.Base64;
import org.jboss.seam.security.management.picketlink.JpaIdentityStore;
import org.picketlink.idm.common.exception.IdentityException;

/**
 * Date: 1/27/13
 *
 * @author T. Curran
 */
public class JpaIdentityStoreWithHashing extends JpaIdentityStore {

	private static final long serialVersionUID = 6538258501489656692L;

	private static final String DefaultEncoding = "UTF-8";
	private static final String HashAlgorithm = "SHA-256";
	private static final String SaltAttribute = "SALT";


	private Class<?> credentialClass;

	public JpaIdentityStoreWithHashing(String id) {
		super(id);
	}

	@Override
	public void bootstrap(org.picketlink.idm.spi.configuration.IdentityStoreConfigurationContext configurationContext)
			throws IdentityException {

		String clsName =
				configurationContext.getStoreConfigurationMetaData()
						.getOptionSingleValue(OPTION_CREDENTIAL_CLASS_NAME);


		if (clsName != null) {
			try {
				credentialClass = Class.forName(clsName);
			} catch (ClassNotFoundException e) {
				throw new IdentityException("Error bootstrapping JpaIdentityStoreWithHashing - invalid credential entity class: " + clsName);
			}
		}
		super.bootstrap(configurationContext);
	}

	@Override
	public boolean validateCredential(org.picketlink.idm.spi.store.IdentityStoreInvocationContext ctx,
									  org.picketlink.idm.spi.model.IdentityObject identityObject, org.picketlink.idm.spi.model.IdentityObjectCredential credential)
			throws IdentityException {
		EntityManager em = getEntityManager(ctx);


		if (credentialClass == com.approachingpi.timetime.data.model.IdentityObjectCredential.class) {

			TypedQuery<IdentityObjectCredential> query = em.createQuery("SELECT a FROM IdentityObjectCredential a WHERE a.identityObject=:object AND a.type=:type", IdentityObjectCredential.class);
			query.setParameter("object", lookupIdentity(identityObject, em));
			query.setParameter("type", lookupCredentialTypeEntity(credential.getType().getName(), em));

			List<IdentityObjectCredential> results = query.getResultList();

			if (results.isEmpty()) return false;

			final String salt = getSalt(ctx, identityObject);

			final byte[] bSalt = Base64.decodeBase64(salt);
			final byte[] proposedDigest = getHash(credential.getValue().toString(), bSalt);

			for (IdentityObjectCredential result : results) {
				final byte[] bDigest = Base64.decodeBase64(result.getValue());

				if (Arrays.equals(proposedDigest, bDigest)) {
					return true;
				}
			}
		}
		return super.validateCredential(ctx, identityObject, credential);
	}

	/**
	 * Gets the salt to use to create the hash of a password.
	 *
	 * @param ctx            IdentityStoreInvocationContext the current context.
	 * @param identityObject IdentityObject the identity to get the salt for.
	 * @return the salt.
	 * @throws IdentityException if the default encoding doesn't exist.
	 */
	private String getSalt(org.picketlink.idm.spi.store.IdentityStoreInvocationContext ctx,
						   org.picketlink.idm.spi.model.IdentityObject identityObject) throws IdentityException {
		EntityManager em = getEntityManager(ctx);

		TypedQuery<IdentityObjectAttribute> query = em.createQuery("SELECT a FROM IdentityObjectAttribute a WHERE a.identityObject=:object AND a.name=:name", IdentityObjectAttribute.class);
		query.setParameter("object", lookupIdentity(identityObject, em));
		query.setParameter("name", SaltAttribute);

		final List<IdentityObjectAttribute> results = query.getResultList();

		return results.size() > 0 ? results.get(0).getValue() : "";
	}

	/**
	 * From a password and a salt returns the corresponding digest
	 *
	 * @param password String The password to encrypt
	 * @param salt     byte[] The salt
	 * @return byte[] The digested password
	 * @throws IdentityException If the hash algorithm doesn't exist or
	 *                           the default encoding doesn't exist
	 */
	private byte[] getHash(String password, byte[] salt) throws IdentityException {
		try {
			final MessageDigest digest = MessageDigest.getInstance(HashAlgorithm);

			digest.reset();
			digest.update(salt);

			return digest.digest(password.getBytes(DefaultEncoding));
		} catch (NoSuchAlgorithmException ex) {
			throw new IdentityException(ex);
		} catch (UnsupportedEncodingException ex) {
			throw new IdentityException(ex);
		}
	}
}
