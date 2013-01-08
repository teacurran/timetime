package com.approachingpi.timetime.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Date: 1/7/13
 *
 * @author T. Curran
 */
public class BaseService {

	@PersistenceContext
	protected EntityManager em;

	// These injections are no longer working since upgrading to jboss as 7.1. I don't know why.
	//	@Inject
	//	protected Messages messages;
	//
	//	@Inject
	//	protected Configuration configuration;

}
