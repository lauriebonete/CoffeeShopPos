package org.pos.coffee.bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public final class Product extends BaseEntity {

	@Column(name="PRODUCT_NAME")
	private String productName;

	@ManyToMany
	@JoinTable(name="RECIPE",
			joinColumns = {@JoinColumn(name="PRODUCT_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="INGREDIENT")
	)
	private List<Ingredient> ingredientList;

	@ManyToMany
	@JoinTable(name="PRODUCT_GROUP",
			joinColumns = {@JoinColumn(name="PRODUCT_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="GROUP", referencedColumnName = "KEY_")
	)
	private List<ReferenceLookUp> productGroupList;

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}
	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}
	public List<ReferenceLookUp> getProductGroupList() {
		return productGroupList;
	}
	public void setProductGroupList(List<ReferenceLookUp> productGroupList) {
		this.productGroupList = productGroupList;
	}
	
}
