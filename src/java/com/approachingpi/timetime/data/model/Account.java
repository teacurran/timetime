package com.approachingpi.timetime.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Access( AccessType.FIELD )
@Cacheable
@NamedQueries({
		@NamedQuery(name = Account.QUERY_ALL,
				query = "SELECT A FROM Account A"),
		@NamedQuery(name = Account.QUERY_BY_ID,
				query = "SELECT A FROM Account A WHERE A.id = :id"),
		@NamedQuery(name = Account.QUERY_BY_EMAIL,
				query = "SELECT A FROM Account A WHERE A.email = :email")
	}
)
public class Account implements java.io.Serializable {

	public static final String QUERY_ALL					= "account.getAll";
	public static final String QUERY_BY_ID					= "account.byId";
	public static final String QUERY_BY_EMAIL				= "account.byEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Company company;

	@Basic
	private String fullName;

	@Basic
	private String email;

	@Basic
	private String password;

	@Basic
	protected String passwordSalt;

	@Basic
	private Integer timezone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModified;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<TimeSegment> timeSegments = new ArrayList<TimeSegment>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<TaskSummary> taskSummaries = new ArrayList<TaskSummary>(0);

	@Basic
	private boolean enabled;

	public Account() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
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

	public List<TimeSegment> getTimeSegments() {
		return timeSegments;
	}

	public void setTimeSegments(List<TimeSegment> timeSegments) {
		this.timeSegments = timeSegments;
	}

	public List<TaskSummary> getTaskSummaries() {
		return taskSummaries;
	}

	public void setTaskSummaries(List<TaskSummary> taskSummaries) {
		this.taskSummaries = taskSummaries;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
}
