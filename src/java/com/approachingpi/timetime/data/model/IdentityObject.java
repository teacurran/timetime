package com.approachingpi.timetime.data.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

/**
 * Date: 1/12/13
 *
 * @author T. Curran
 */
@Entity
public class IdentityObject implements Serializable {

	private static final long serialVersionUID = -8615976442954753614L;

	@Id
	@GeneratedValue
	private Long id;

	@IdentityProperty(PropertyType.NAME)
	private String name;

	@ManyToOne
	@IdentityProperty(PropertyType.TYPE)
	private IdentityObjectType type;

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

	public IdentityObjectType getType() {
		return type;
	}

	public void setType(IdentityObjectType type) {
		this.type = type;
	}
}