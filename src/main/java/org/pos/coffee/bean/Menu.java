package org.pos.coffee.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="MENU")
public class Menu extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="PRODUCT", referencedColumnName = "ID")
	private Product product;

	@Column(name = "PRODUCT", insertable = false, updatable = false)
	private Long productId;

	@ManyToOne
	@JoinColumn(name="MEAL", referencedColumnName = "ID")
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
}
