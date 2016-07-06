package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;
import org.evey.bean.ReferenceLookUp;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Laurie on 1/20/2016.
 */
@Entity
@Table(name = "BRANCH")
public class Branch extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "BRANCH_ID", referencedColumnName = "ID")
    private ReferenceLookUp branch;

    @ElementCollection
    @CollectionTable(name="NETWORK",
            joinColumns=@JoinColumn(name="BRANCH_ID"))
    @Column(name = "MAC_ADDRESS")
    private List<String> macAddresses;

    public ReferenceLookUp getBranch() {
        return branch;
    }

    public void setBranch(ReferenceLookUp branch) {
        this.branch = branch;
    }

    public List<String> getMacAddresses() {
        return macAddresses;
    }

    public void setMacAddresses(List<String> macAddresses) {
        this.macAddresses = macAddresses;
    }
}
