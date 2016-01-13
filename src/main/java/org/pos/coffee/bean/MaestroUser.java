package org.pos.coffee.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class MaestroUser{

	private ReferenceLookUp branch;

	public ReferenceLookUp getBranch() {
		return branch;
	}

	public void setBranch(ReferenceLookUp branch) {
		this.branch = branch;
	}

}
