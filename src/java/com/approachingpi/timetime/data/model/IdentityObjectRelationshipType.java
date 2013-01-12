package com.approachingpi.timetime.data.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

/**
 * Date: 1/12/13
 *
 * @author T. Curran
 */
@Entity
public class IdentityObjectRelationshipType implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@IdentityProperty(PropertyType.NAME)
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