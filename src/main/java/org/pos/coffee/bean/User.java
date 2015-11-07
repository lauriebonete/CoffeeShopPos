package org.pos.coffee.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
public class User extends Person {

	@Column(name="USERNAME")
	private String username;

	@Column(name="PASSWORD")
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
	
}
