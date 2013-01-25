package com.approachingpi.timetime.data.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.security.annotations.management.EntityType;
import org.jboss.seam.security.annotations.management.IdentityEntity;

/**
 * Date: 1/12/13
 *
 * @author T. Curran
 */
@Entity
@IdentityEntity(EntityType.IDENTITY_ROLE_NAME)
public class IdentityRoleName implements Serializable {

	private static final long serialVersionUID = -419893700810364565L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}