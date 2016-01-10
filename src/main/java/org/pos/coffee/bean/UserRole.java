package org.pos.coffee.bean;


import org.evey.bean.BaseEntity;

import javax.persistence.*;

/**
 * Created by kenji on 11/25/2015.
 */
@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity {

    @Column(name="ROLE")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
