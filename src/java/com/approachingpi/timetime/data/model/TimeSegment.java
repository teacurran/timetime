package com.approachingpi.timetime.data.model;

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
public class TimeSegment implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Task task;

	@ManyToOne
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStart;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateReported;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnd;

	public TimeSegment() {
	}

	public TimeSegment(Task task, Account account) {
		this.task = task;
		this.account = account;
	}
	public TimeSegment(Task task, Account account, Date dateStart,
			Date dateReported, Date dateEnd) {
		this.task = task;
		this.account = account;
		this.dateStart = dateStart;
		this.dateReported = dateReported;
		this.dateEnd = dateEnd;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateReported() {
		return this.dateReported;
	}

	public void setDateReported(Date dateReported) {
		this.dateReported = dateReported;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

}
