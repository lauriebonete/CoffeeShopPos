package org.evey.persistence;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.evey.bean.BaseEntity;

public class EntityListener {

	@PrePersist
	public void setDates(BaseEntity entity) {
		// set dateCreated and dateUpdated fields
		Date now = new Date();
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		entity.setUpdateDate(now);
		entity.setCreatedByUsername(entity.getAuditUsername());
	}

	@PreUpdate
	public void updateDates(BaseEntity entity) {
		// set dateUpdated fields
		entity.setUpdateDate(new Date());
	}
}
