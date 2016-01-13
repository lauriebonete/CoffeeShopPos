package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="AUTHORITY")
public class Authority extends BaseEntity {

	@Column(name="PRIVILEGE")
	private String privilege;
	
}
