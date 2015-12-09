package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT_MEAL")
public class ProductMeal extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="PRODUCT", referencedColumnName = "ID")
	private Product product;

	@Column(name = "PRODUCT", insertable = false, updatable = false)
	private Long productId;

	@Column(name="QUANTITY")
	private Double quantity;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
