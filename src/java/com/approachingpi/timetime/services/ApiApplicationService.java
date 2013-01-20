package com.approachingpi.timetime.services;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.approachingpi.timetime.data.model.ApiApplication;

/**
 * Date: 1/20/2013
 *
 * @author T. Curran
 */
@Named
public class ApiApplicationService {

	@PersistenceContext
	protected EntityManager em;

	public ApiApplication findById(final String inUuid) {

		ApiApplication apiApplication = em.find(ApiApplication.class, inUuid);

		return apiApplication;
	}
}
