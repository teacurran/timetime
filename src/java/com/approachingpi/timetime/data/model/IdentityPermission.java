package com.approachingpi.timetime.data.model;

import java.io.Serializable;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.jboss.seam.security.annotations.permission.PermissionProperty;
import org.jboss.seam.security.annotations.permission.PermissionPropertyType;

/**
 * Date: 1/12/13
 *
 * @author T. Curran
 */
@Entity
public class IdentityPermission implements Serializable {

	private static final long serialVersionUID = -4445887695989506783L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	@PermissionProperty(PermissionPropertyType.IDENTITY)
	private IdentityObject identityObject;

	@ManyToOne
	@PermissionProperty(PermissionPropertyType.RELATIONSHIP_TYPE)
	private IdentityObjectRelationshipType relationshipType;

	@PermissionProperty(PermissionPropertyType.RELATIONSHIP_NAME)
	private String relationshipName;

	@PermissionProperty(PermissionPropertyType.RESOURCE)
	private String resource;

	@PermissionProperty(PermissionPropertyType.PERMISSION)
	private String permission;

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

	public IdentityObjectRelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(IdentityObjectRelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}