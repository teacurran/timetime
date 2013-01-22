package com.approachingpi.timetime.data.model;

import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Access( AccessType.FIELD )
@Cacheable
public class TimeSummary implements java.io.Serializable {

	public static String STATUS_PENDING;
	public static String STATUS_SUBMITTED;
	public static String STATUS_REJECTED;
	public static String STATUS_ACCEPTED;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@ManyToOne
	protected Task task;

	@ManyToOne
	protected Account account;

	@ManyToOne
	protected Account reviewer;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date date;

	@Basic
	protected String status;

	@Basic
	protected int minutesRecorded;

	@Basic
	protected int minutesEntered;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateModified;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateSubmitted;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateApproved;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateRejected;

	@Lob
	protected String notes;

	public TimeSummary() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Account getReviewer() {
		return reviewer;
	}

	public void setReviewer(Account reviewer) {
		this.reviewer = reviewer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMinutesRecorded() {
		return minutesRecorded;
	}

	public void setMinutesRecorded(int minutesRecorded) {
		this.minutesRecorded = minutesRecorded;
	}

	public int getMinutesEntered() {
		return minutesEntered;
	}

	public void setMinutesEntered(int minutesEntered) {
		this.minutesEntered = minutesEntered;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public Date getDateRejected() {
		return dateRejected;
	}

	public void setDateRejected(Date dateRejected) {
		this.dateRejected = dateRejected;
	}
}
