package com.approachingpi.timetime.producers;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.solder.core.ExtensionManaged;

/**
 * Date: 1/26/13
 *
 * @author T. Curran
 */
public class EntityManagerProducer {
	@Produces
	@ExtensionManaged
	@ConversationScoped
	@PersistenceUnit
	EntityManagerFactory emf;
}
