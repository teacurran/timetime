package com.approachingpi.timetime.data.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Access( AccessType.FIELD )
@Cacheable
public class Company implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@Basic
	protected String name;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( length = 0 )
	protected Date dateCreated;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( length = 0 )
	protected Date dateModified;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	protected Set<Client> clients = new HashSet<Client>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	protected Set<Account> accounts = new HashSet<Account>(0);

	public Company() {
		this.dateCreated = new Date();
		this.dateModified = new Date();
	}

	public Company(String name) {
		this.name = name;
		this.dateCreated = new Date();
		this.dateModified = new Date();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
}
