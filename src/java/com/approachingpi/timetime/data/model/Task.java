package com.approachingpi.timetime.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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

	@ManyToOne
	protected Company company;

	@Basic
	protected Integer parentId;

	@Basic
	protected String name;

	@Column(length = 20)
	protected String nameShort;

	@Basic
	protected String path;

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
	protected List<TaskSummary> taskSummaries = new ArrayList<TaskSummary>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	protected List<TimeSegment> timeSegments = new ArrayList<TimeSegment>(0);

	public Task() {
	}

	public Task(Company company) {
		this.company = company;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameShort() {
		return this.nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
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

	public List<TaskSummary> getTaskSummaries() {
		return this.taskSummaries;
	}

	public void setTaskSummaries(List<TaskSummary> taskSummaries) {
		this.taskSummaries = taskSummaries;
	}

	public List<TimeSegment> getTimeSegments() {
		return this.timeSegments;
	}

	public void setTimeSegments(List<TimeSegment> timeSegments) {
		this.timeSegments = timeSegments;
	}

}
