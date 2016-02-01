package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.*;

/**
 * Created by Laurie on 12/16/2015.
 */
@Entity
@Table(name = "P_GROUP")
public class ProductGroup extends BaseEntity {

    @Column(name = "GROUP_NAME")
    private String productGroupName;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "IMAGE", referencedColumnName = "ID")
    private FileDetail groupImage;

    @Column(name = "IMAGE", insertable = false, updatable = false)
    private Long groupImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    private ReferenceLookUp category;

    @Column(name = "CATEGORY", insertable = false, updatable = false)
    private Long categoryId;

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public FileDetail getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(FileDetail groupImage) {
        this.groupImage = groupImage;
    }

    public Long getGroupImageId() {
        return groupImageId;
    }

    public void setGroupImageId(Long groupImageId) {
        this.groupImageId = groupImageId;
    }

    public ReferenceLookUp getCategory() {
        return category;
    }

    public void setCategory(ReferenceLookUp category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
