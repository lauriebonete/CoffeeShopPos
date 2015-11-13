package org.pos.coffee.bean;

import javax.persistence.*;

@Entity
@Table(name="INGREDIENT")
public class Ingredient extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="ITEM", referencedColumnName = "ID")
	private Item item;

	@Column(name = "ITEM", insertable = false, updatable = false)
	private Long itemId;

	@Column(name="QUANTITY")
	private Double quantity;

	@ManyToOne
	@JoinColumn(name="PRODUCT", referencedColumnName = "ID")
	private Product product;

	@ManyToOne
	@JoinColumn(name="SIZE_ID", referencedColumnName = "ID")
	private ReferenceLookUp size;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public ReferenceLookUp getSize() {
		return size;
	}
	public void setSize(ReferenceLookUp size) {
		this.size = size;
	}
}
