package org.evey.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;
import org.evey.annotation.UniqueField;
import org.evey.persistence.EntityListener;
import org.evey.security.SessionUser;
import org.evey.utility.SecurityUtil;

/*
 * Base class for all objects contains basic attributes
 * 
 * Author: Laurie Bonete 
 */

@MappedSuperclass
@EntityListeners(EntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 2880646613100616928L;
	private static Logger logger = Logger.getLogger(BaseEntity.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@UniqueField
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

	@Transient
	private Boolean skipAudit;

	@Transient
	private Map<String,String> orderBy;

	private transient String auditUsername;
	
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

	public Boolean getSkipAudit() {
		return skipAudit;
	}

	public void setSkipAudit(Boolean skipAudit) {
		this.skipAudit = skipAudit;
	}

	public Map<String, String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Map<String, String> orderBy) {
		this.orderBy = orderBy;
	}

	public String getAuditUsername() {
		SessionUser user = SecurityUtil.getSessionUser();
		return user.getUsername();
	}

	public void setAuditUsername(String auditUsername) {
		this.auditUsername = auditUsername;
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

	@PrePersist
	protected void prePersist(){}
}
