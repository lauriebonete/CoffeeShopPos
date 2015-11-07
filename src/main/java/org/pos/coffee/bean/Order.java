package org.pos.coffee.bean;

import javax.persistence.*;

@Entity
@Table(name="ORDER")
public class Order extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="SALE_ID", referencedColumnName = "ID")
	private Sale sale;

	@Column(name = "SALE_ID", insertable = false, updatable = false)
	private Long saleId;

	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", referencedColumnName = "ID")
	private Product product;

	@Column(name = "PRODUCT_ID", insertable = false, updatable = false)
	private Long productId;

	@ManyToOne
	@JoinColumn(name="MEAL_ID", referencedColumnName = "ID")
	private Meal meal;

	@Column(name = "MEAL_ID", insertable = false, updatable = false)
	private Long mealId;

	@ManyToOne
	@JoinColumn(name="ADD_ON", referencedColumnName = "ID")
	private Ingredient additional;

	@Column(name = "ADD_ON", insertable = false, updatable = false)
	private Long additionalId;

	@Column(name="QUANTITY")
	private Double quantity;

	@Column(name="LINE_PRICE")
	private Double totalLinePrice;

	@Column(name="LINE_EXPENSE")
	private Double totalLineExpense;

	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
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
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getTotalLinePrice() {
		return totalLinePrice;
	}
	public void setTotalLinePrice(Double totalLinePrice) {
		this.totalLinePrice = totalLinePrice;
	}
	public Double getTotalLineExpense() {
		return totalLineExpense;
	}
	public void setTotalLineExpense(Double totalLineExpense) {
		this.totalLineExpense = totalLineExpense;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Ingredient getAdditional() {
		return additional;
	}

	public void setAdditional(Ingredient additional) {
		this.additional = additional;
	}

	public Long getAdditionalId() {
		return additionalId;
	}

	public void setAdditionalId(Long additionalId) {
		this.additionalId = additionalId;
	}
}
