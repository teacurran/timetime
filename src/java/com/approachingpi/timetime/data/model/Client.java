package com.approachingpi.timetime.data.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Date: 1/22/13
 *
 * @author T. Curran
 */
@Entity
@Access( AccessType.FIELD )
@Cacheable
public class Client {

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	protected Set<Project> projects = new HashSet<Project>(0);

	@ManyToOne
	private Company company;

	@Basic
	protected Boolean deleted = false;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "clients_tasks", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	protected List<Task> tasks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
