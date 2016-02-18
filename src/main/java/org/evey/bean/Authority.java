package org.evey.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="AUTHORITY")
public class Authority extends BaseEntity {

	@Column(name="PRIVILEGE")
	private String access;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
	@JsonBackReference
	private UserRole userRole;

	@Column(name = "ROLE_ID", insertable = false, updatable = false)
	private Long userRoleId;

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
}
