package com.approachingpi.timetime.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

@Entity
@Access( AccessType.FIELD )
@Cacheable
public class Task implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "projects_tasks", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	protected List<Project> projects;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "clients_tasks", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	protected List<Client> clients;

	@Basic
	protected String name;

	@Column(length = 20)
	protected String code;

	@Basic
	protected String path;

	@Basic
	protected Boolean deleted = false;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateModified;

	@ManyToOne
	protected Account createdAccount;

	@ManyToOne
	protected Account modifiedAccount;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateLastEvent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	protected List<TimeSummary> timeSummaries = new ArrayList<TimeSummary>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	protected List<TimeSegment> timeSegments = new ArrayList<TimeSegment>(0);

	public Task() {
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

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public Account getCreatedAccount() {
		return createdAccount;
	}

	public void setCreatedAccount(Account createdAccount) {
		this.createdAccount = createdAccount;
	}

	public Account getModifiedAccount() {
		return modifiedAccount;
	}

	public void setModifiedAccount(Account modifiedAccount) {
		this.modifiedAccount = modifiedAccount;
	}

	public Date getDateLastEvent() {
		return this.dateLastEvent;
	}

	public void setDateLastEvent(Date dateLastEvent) {
		this.dateLastEvent = dateLastEvent;
	}

	public List<TimeSummary> getTimeSummaries() {
		return this.timeSummaries;
	}

	public void setTimeSummaries(List<TimeSummary> taskSummaries) {
		this.timeSummaries = taskSummaries;
	}

	public List<TimeSegment> getTimeSegments() {
		return this.timeSegments;
	}

	public void setTimeSegments(List<TimeSegment> timeSegments) {
		this.timeSegments = timeSegments;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
}
