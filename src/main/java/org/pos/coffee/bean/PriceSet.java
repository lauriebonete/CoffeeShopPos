package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.evey.annotation.JoinList;
import org.evey.annotation.JoinSet;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by Laurie on 12/7/2015.
 */
@Entity
@Table(name = "PRICE_SET")
public class PriceSet extends BaseEntity{

    @Column(name = "SET_NAME")
    private String priceSetName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-YYYY")
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-YYYY")
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name="IS_LINE")
    private Boolean isForLine;

    @Column(name="IS_PERCENTAGE")
    private Boolean isPercentage;

    @Column(name="IS_DISCOUNT")
    private Boolean isDiscount;

    @Column(name="IS_BY_PROD")
    private Boolean isTriggeredByProduct;

    @JoinSet
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PRICE_SET_PRODUCT",
            joinColumns = {@JoinColumn(name="PRICE_SET", referencedColumnName = "ID")},
            inverseJoinColumns = @JoinColumn(name="PRODUCT", referencedColumnName = "ID")
    )
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Set<Product> product;

    @Column(name="IS_BY_GROUP")
    private Boolean isTriggeredByPromoGroup;

    @JoinSet
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PRICE_SET_GROUP",
            joinColumns = {@JoinColumn(name="PRICE_SET", referencedColumnName = "ID")},
            inverseJoinColumns = @JoinColumn(name="PROMO_GROUP", referencedColumnName = "ID")
    )
    private Set<ReferenceLookUp> promoGroup;

    @Column(name = "IS_BY_QUANTITY")
    private Boolean isQuantityTriggered;

    @Column(name = "MIN_QUANTITY")
    private Long minQuantity;

    @Column(name = "IS_BY_PRICE")
    private Boolean isPriceTriggered;

    @Column(name = "MIN_PRICE")
    private Double minPrice;

    @Column(name = "SET_MODIFIER")
    private Double priceSetModifier;

    @Column(name = "IS_VOUCHER")
    private Boolean isVoucherTriggered;

    @Column(name = "VOUCHER")
    private String voucherCode;

    @Column(name = "STOP_OTHER")
    private Boolean stopOtherPriceSet;

    @Transient
    private Date lookForStartDate;

    @Transient
    private Date lookForEndDate;

    public String getPriceSetName() {
        return priceSetName;
    }

    public void setPriceSetName(String priceSetName) {
        this.priceSetName = priceSetName;
    }

    public Boolean getIsForLine() {
        return isForLine;
    }

    public void setIsForLine(Boolean isForLine) {
        this.isForLine = isForLine;
    }

    public Boolean getIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public Boolean getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Boolean isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Boolean getIsTriggeredByProduct() {
        return isTriggeredByProduct;
    }

    public void setIsTriggeredByProduct(Boolean isTriggeredByProduct) {
        this.isTriggeredByProduct = isTriggeredByProduct;
    }

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

    public Boolean getIsTriggeredByPromoGroup() {
        return isTriggeredByPromoGroup;
    }

    public void setIsTriggeredByPromoGroup(Boolean isTriggeredByPromoGroup) {
        this.isTriggeredByPromoGroup = isTriggeredByPromoGroup;
    }

    public Set<ReferenceLookUp> getPromoGroup() {
        return promoGroup;
    }

    public void setPromoGroup(Set<ReferenceLookUp> promoGroup) {
        this.promoGroup = promoGroup;
    }

    public Boolean getIsQuantityTriggered() {
        return isQuantityTriggered;
    }

    public void setIsQuantityTriggered(Boolean isQuantityTriggered) {
        this.isQuantityTriggered = isQuantityTriggered;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Boolean getIsPriceTriggered() {
        return isPriceTriggered;
    }

    public void setIsPriceTriggered(Boolean isPriceTriggered) {
        this.isPriceTriggered = isPriceTriggered;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getPriceSetModifier() {
        return priceSetModifier;
    }

    public void setPriceSetModifier(Double priceSetModifier) {
        this.priceSetModifier = priceSetModifier;
    }

    public Boolean getIsVoucherTriggered() {
        return isVoucherTriggered;
    }

    public void setIsVoucherTriggered(Boolean isVoucherTriggered) {
        this.isVoucherTriggered = isVoucherTriggered;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLookForStartDate() {
        return lookForStartDate;
    }

    public void setLookForStartDate(Date lookForStartDate) {
        this.lookForStartDate = lookForStartDate;
    }

    public Date getLookForEndDate() {
        return lookForEndDate;
    }

    public void setLookForEndDate(Date lookForEndDate) {
        this.lookForEndDate = lookForEndDate;
    }

    public Boolean getStopOtherPriceSet() {
        return stopOtherPriceSet;
    }

    public void setStopOtherPriceSet(Boolean stopOtherPriceSet) {
        this.stopOtherPriceSet = stopOtherPriceSet;
    }

    @JsonView
    public String displayStartDate(){
        if(this.startDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(this.startDate);
        }
        return "";
    }

    @JsonView
    public String displayEndDate(){
        if(this.endDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(this.endDate);
        }

        return "";
    }
}
