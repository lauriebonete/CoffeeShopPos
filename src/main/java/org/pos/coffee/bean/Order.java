package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.evey.annotation.JoinList;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ORDER_LINE")
public class Order extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="SALE_ID", referencedColumnName = "ID")
	@JsonBackReference
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
	@JoinColumn(name="LIST_PRICE", referencedColumnName = "ID")
	private ListPrice listPrice;

	@Column(name = "LIST_PRICE", insertable = false, updatable = false)
	private Long listPriceId;


	@ManyToOne
	@JoinColumn(name="ADD_ON", referencedColumnName = "ID")
	private Ingredient additional;

	@Column(name = "ADD_ON", insertable = false, updatable = false)
	private Long additionalId;

	@Column(name="QUANTITY")
	private Long quantity;

	@JoinList
	@ManyToMany
	@JoinTable(name="ORD_PRICE_ADJST",
			joinColumns = {@JoinColumn(name="ORDER_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="PRICE_SET_ID", referencedColumnName = "ID")
	)
	private List<PriceSet> appliedPriceSet;

	@Column(name="LINE_PRICE")
	private Double totalLinePrice;

	@Column(name = "GROSS_PRICE")
	private Double grossLinePrice;

	@Column(name = "TOTAL_DISC")
	private Double totalPriceSetDisc;

	@Column(name = "TOTAL_SUR")
	private Double totalPriceSetSur;

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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
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

	public List<PriceSet> getAppliedPriceSet() {
		return appliedPriceSet;
	}

	public void setAppliedPriceSet(List<PriceSet> appliedPriceSet) {
		this.appliedPriceSet = appliedPriceSet;
	}

	public ListPrice getListPrice() {
		return listPrice;
	}

	public void setListPrice(ListPrice listPrice) {
		this.listPrice = listPrice;
	}

	public Long getListPriceId() {
		return listPriceId;
	}

	public void setListPriceId(Long listPriceId) {
		this.listPriceId = listPriceId;
	}

	public Double getGrossLinePrice() {
		return grossLinePrice;
	}

	public void setGrossLinePrice(Double grossLinePrice) {
		this.grossLinePrice = grossLinePrice;
	}

	public Double getTotalPriceSetDisc() {
		return totalPriceSetDisc;
	}

	public void setTotalPriceSetDisc(Double totalPriceSetDisc) {
		this.totalPriceSetDisc = totalPriceSetDisc;
	}

	public Double getTotalPriceSetSur() {
		return totalPriceSetSur;
	}

	public void setTotalPriceSetSur(Double totalPriceSetSur) {
		this.totalPriceSetSur = totalPriceSetSur;
	}
}
