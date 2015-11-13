package org.pos.coffee.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.pos.coffee.persistence.EntityListener;
import org.pos.coffee.web.json.Scope;

/*
 * Base class for all objects contains basic attributes
 * 
 * Author: Laurie Bonete 
 */

@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 2880646613100616928L;
	private static Logger logger = Logger.getLogger(BaseEntity.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonView(Scope.Search.class)
	private Long id;
	
	@Column(name = "CREATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "UPDATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name = "CREATEDBY")
	private String createdByUsername;
	
	@Version
    @Column(name = "VERSION")
	private Long version;
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreatedByUsername() {
		return createdByUsername;
	}
	public void setCreatedByUsername(String createdByUsername) {
		this.createdByUsername = createdByUsername;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
	}

	public Boolean isNew() {
		return this.getId() == null || this.getId() <= 0;
	}
}
