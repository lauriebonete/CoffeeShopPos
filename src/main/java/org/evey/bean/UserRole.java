package org.evey.bean;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.evey.annotation.UniqueField;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by kenji on 11/25/2015.
 */
@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity {

    @Column(name="ROLE_NAME", unique=true, nullable=false)
    @UniqueField
    private String roleName;

    @Column(name="ROLE_DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Authority> authorities;

    private transient List<Authority> authorityList;

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
