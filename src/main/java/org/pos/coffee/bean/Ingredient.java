package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.evey.bean.BaseEntity;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	@JsonBackReference
	private Product product;

	@Column(name = "PRODUCT_ID", insertable = false, updatable = false)
	private Long productId;

	@Column(name="IS_BASE")
	private Boolean isBase;


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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Boolean getIsBase() {
		return isBase;
	}

	public void setIsBase(Boolean isBase) {
		this.isBase = isBase;
	}

	@Override
	protected void prePersist() {
		if(this.isBase==null){
			this.isBase = false;
		}
	}
}
