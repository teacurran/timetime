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
public class IdentityObjectRelationship implements Serializable {

	private static final long serialVersionUID = -7326553925808512560L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	@IdentityProperty(PropertyType.TYPE)
	private IdentityObjectRelationshipType relationshipType;

	@ManyToOne
	@IdentityProperty(PropertyType.RELATIONSHIP_FROM)
	private IdentityObject from;

	@ManyToOne
	@IdentityProperty(PropertyType.RELATIONSHIP_TO)
	private IdentityObject to;

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

	public IdentityObjectRelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(IdentityObjectRelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public IdentityObject getFrom() {
		return from;
	}

	public void setFrom(IdentityObject from) {
		this.from = from;
	}

	public IdentityObject getTo() {
		return to;
	}

	public void setTo(IdentityObject to) {
		this.to = to;
	}
}