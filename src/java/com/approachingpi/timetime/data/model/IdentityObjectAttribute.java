package com.approachingpi.timetime.data.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.seam.security.annotations.management.EntityType;
import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

/**
 * Date: 1/24/13
 *
 * @author T. Curran
 */
@Entity
@IdentityEntity(EntityType.IDENTITY_ATTRIBUTE)
public class IdentityObjectAttribute implements Serializable {

	private static final long serialVersionUID = -1846423703184923605L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private IdentityObject identityObject;

	@IdentityProperty(PropertyType.NAME)
	private String name;

	@IdentityProperty(PropertyType.VALUE)
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IdentityObject getIdentityObject() {
		return identityObject;
	}

	public void setIdentityObject(IdentityObject identityObject) {
		this.identityObject = identityObject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
