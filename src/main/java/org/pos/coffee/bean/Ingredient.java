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
}
