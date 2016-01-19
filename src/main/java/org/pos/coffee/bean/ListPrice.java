package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="LIST_PRICE")
public class ListPrice extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT")
	@JsonBackReference
	private Product product;

	@Column(name = "PRODUCT", insertable = false, updatable = false)
	private Long productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEAL")
	private Meal meal;

	@Column(name = "MEAL", insertable = false, updatable = false)
	private Long mealId;

	@Column(name="PRICE")
	private Double price;

	@Column(name="EFFECTIVITY_DATE")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
	@Temporal(TemporalType.DATE)
	private Date effectivityDate;

	@Column(name="END_DATE")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name="IS_PRODUCT")
	private Boolean isProduct;

	private transient String displayName;

	private transient String displayEffectivityDate;

	private transient String displayEndDate;

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Meal getMeal() {
		return meal;
	}
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getEffectivityDate() {
		return effectivityDate;
	}
	public void setEffectivityDate(Date effectivityDate) {
		this.effectivityDate = effectivityDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public Boolean getIsProduct() {
		return isProduct;
	}

	public void setIsProduct(Boolean isProduct) {
		this.isProduct = isProduct;
	}

	public String getDisplayName() {
		if(this.product!=null){
			return this.product.getProductName();
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayEffectivityDate() {
		if(this.effectivityDate!=null){
			return new SimpleDateFormat("MM-dd-yyyy").format(this.effectivityDate);
		}
		return displayEffectivityDate;
	}

	public void setDisplayEffectivityDate(String displayEffectivityDate) {
		this.displayEffectivityDate = displayEffectivityDate;
	}

	public String getDisplayEndDate() {
		if(this.endDate!=null){
			return new SimpleDateFormat("MM-dd-yyyy").format(this.endDate);
		}

		return displayEndDate;
	}

	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}

	@PrePersist
	public void prePersist(){
		if(this.product != null){
			this.isProduct = true;
		} else if(this.product == null && this.meal !=null){
			this.isProduct = false;
		}
	}
}
