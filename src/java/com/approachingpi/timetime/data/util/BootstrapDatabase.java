package com.approachingpi.timetime.data.util;

import java.util.List;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.approachingpi.timetime.data.model.IdentityObject;
import com.approachingpi.timetime.data.model.IdentityObjectCredentialType;
import com.approachingpi.timetime.data.model.IdentityObjectRelationship;
import com.approachingpi.timetime.data.model.IdentityObjectRelationshipType;
import com.approachingpi.timetime.data.model.IdentityObjectType;
import com.approachingpi.timetime.data.model.IdentityRoleName;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;

/**
 * Date: 1/26/13
 *
 * @author T. Curran
 */
public class BootstrapDatabase {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void loadData(@Observes @Initialized WebApplication webapp) {
		// Roles
		createUniqueIdentityRoleName("admin");
		createUniqueIdentityRoleName("user");


		// Object types
		createUniqueIdentityObjectType("USER");
		createUniqueIdentityObjectType("GROUP");


		// Objects
//	        IdentityObject user = new IdentityObject();
//	        user.setName("shane");
//	        user.setType(USER);
//	        entityManager.persist(user);
//
//	        IdentityObject demo = new IdentityObject();
//	        demo.setName("demo");
//	        demo.setType(USER);
//	        entityManager.persist(demo);
//
//	        IdentityObject headOffice = new IdentityObject();
//	        headOffice.setName("Head Office");
//	        headOffice.setType(GROUP);
//	        entityManager.persist(headOffice);
//
//	        IdentityObject foo = new IdentityObject();
//	        foo.setName("foo");
//	        foo.setType(USER);
//	        entityManager.persist(foo);

		// Credential types
		createUniqueIdentityCredentialType("PASSWORD");

		// Credentials
//	        IdentityObjectCredential userPassword = new IdentityObjectCredential();
//	        userPassword.setIdentityObject(user);
//	        userPassword.setType(PASSWORD);
//	        userPassword.setValue("password");
//	        entityManager.persist(userPassword);
//
//	        IdentityObjectCredential demoPassword = new IdentityObjectCredential();
//	        demoPassword.setIdentityObject(demo);
//	        demoPassword.setType(PASSWORD);
//	        demoPassword.setValue("demo");
//	        entityManager.persist(demoPassword);

		// Object relationship types
		createUniqueIdentityObjectRelationshipType("JBOSS_IDENTITY_MEMBERSHIP");
		createUniqueIdentityObjectRelationshipType("JBOSS_IDENTITY_ROLE");

		// Object relationships
//	        IdentityObjectRelationship demoAdminRole = new IdentityObjectRelationship();
//	        demoAdminRole.setName("admin");
//	        demoAdminRole.setRelationshipType(jbossIdentityRole);
//	        demoAdminRole.setFrom(headOffice);
//	        demoAdminRole.setTo(demo);
//	        entityManager.persist(demoAdminRole);
	}

	public void createUniqueIdentityRoleName(String name) {
		TypedQuery<IdentityRoleName> query = entityManager.createQuery("SELECT a from IdentityRoleName a WHERE a.name=:name", IdentityRoleName.class);
		query.setParameter("name", name);
		List<IdentityRoleName> results = query.getResultList();

		if (results == null || results.size() == 0) {
			IdentityRoleName obj = new IdentityRoleName();
			obj.setName(name);
			entityManager.persist(obj);
		}
	}

	public void createUniqueIdentityObjectType(String name) {
		TypedQuery<IdentityObjectType> query = entityManager.createQuery("SELECT a from IdentityObjectType a WHERE a.name=:name", IdentityObjectType.class);
		query.setParameter("name", name);
		List<IdentityObjectType> results = query.getResultList();

		if (results == null || results.size() == 0) {
			IdentityObjectType obj = new IdentityObjectType();
			obj.setName(name);
			entityManager.persist(obj);
		}
	}

	public void createUniqueIdentityCredentialType(String name) {
		TypedQuery<IdentityObjectCredentialType> query = entityManager.createQuery("SELECT a from IdentityObjectCredentialType a WHERE a.name=:name", IdentityObjectCredentialType.class);
		query.setParameter("name", name);
		List<IdentityObjectCredentialType> results = query.getResultList();

		if (results == null || results.size() == 0) {
			IdentityObjectCredentialType obj = new IdentityObjectCredentialType();
			obj.setName(name);
			entityManager.persist(obj);
		}
	}

	public void createUniqueIdentityObjectRelationshipType(String name) {
		TypedQuery<IdentityObjectRelationshipType> query = entityManager.createQuery("SELECT a from IdentityObjectRelationshipType a WHERE a.name=:name", IdentityObjectRelationshipType.class);
		query.setParameter("name", name);
		List<IdentityObjectRelationshipType> results = query.getResultList();

		if (results == null || results.size() == 0) {
			IdentityObjectRelationshipType obj = new IdentityObjectRelationshipType();
			obj.setName(name);
			entityManager.persist(obj);
		}
	}

}
