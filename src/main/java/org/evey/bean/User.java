package org.evey.bean;

import org.evey.annotation.UniqueField;
import org.pos.coffee.bean.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User extends BaseEntity{

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="PERSON_ID")
	private Person person;

	@Column(name = "PERSON_ID", insertable = false, updatable = false)
	private Long personId;

	@Column(name="USERNAME", unique=true, nullable=false)
	private String username;

	@Column(name="PASSWORD", nullable=false)
	private String password;

	@Column(name="HIRED_DATE")
	@Temporal(TemporalType.DATE)
	private Date hiredDate;

	@Column(name="UNTIL_DATE")
	@Temporal(TemporalType.DATE)
	private Date untilDate;

	@Column(name="EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date accountExpirationDate;

	@Column(name="PIN_DIGIT", unique=true, nullable=false)
	@UniqueField
	private String pinDigit;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_USER_ROLE",
			joinColumns = { @JoinColumn(name = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "USER_ROLE_ID") })
	private Set<UserRole> userRole;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_AUTHORITY",
			joinColumns = { @JoinColumn(name = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "USER_AUTHORITY_ID") })
	private Set<Authority> authorities;

	//not yet implemented
//	private Role role;
//	private Schedule schedule;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}
	public Date getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}
	public Date getAccountExpirationDate() {
		return accountExpirationDate;
	}
	public void setAccountExpirationDate(Date accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	public String getPinDigit() {
		return pinDigit;
	}

	public void setPinDigit(String pinDigit) {
		this.pinDigit = pinDigit;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

}
