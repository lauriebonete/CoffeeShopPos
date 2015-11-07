package org.pos.coffee.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="MAESTRO_USER")
public class MaestroUser extends User {

	@ManyToOne
	@JoinColumn(name="BRANCH", referencedColumnName = "KEY_")
	private ReferenceLookUp branch;

	public ReferenceLookUp getBranch() {
		return branch;
	}

	public void setBranch(ReferenceLookUp branch) {
		this.branch = branch;
	}
	
}
