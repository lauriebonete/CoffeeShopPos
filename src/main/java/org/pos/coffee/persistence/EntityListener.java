package org.pos.coffee.persistence;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.pos.coffee.bean.BaseEntity;

public class EntityListener {

	@PrePersist
	public void setDates(BaseEntity entity) {
		// set dateCreated and dateUpdated fields
		Date now = new Date();
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		entity.setUpdateDate(now);
	}

	@PreUpdate
	public void updateDates(BaseEntity entity) {
		// set dateUpdated fields
		entity.setUpdateDate(new Date());
	}
}
