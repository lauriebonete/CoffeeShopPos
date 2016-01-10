package org.evey.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Laurie on 1/7/2016.
 */
@Entity
@Table(name = "SEQUENCE_")
public class Sequence extends BaseEntity {

    @Column(name = "KEY_", unique = true, nullable = false)
    private String key;

    @Column(name = "VALUE_")
    private Long value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public final synchronized void incrementValue(int step) {
        if(this.value == null) {
            this.value = Long.valueOf(1l);
        } else {
            this.value += step;
        }
    }
}
