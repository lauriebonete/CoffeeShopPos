package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
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
	@Temporal(TemporalType.DATE)
	private Date effectivityDate;

	@Column(name="END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

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

	@JsonView
	public String displayName(){
		if(productId!=null){
			return product.getProductName();
		} else if(mealId!=null){
			return meal.getMealName();
		}
		return "";
	}
}
