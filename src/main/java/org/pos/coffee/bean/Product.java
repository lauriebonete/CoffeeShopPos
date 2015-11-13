package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonView;
import org.pos.coffee.web.json.Scope;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseEntity {

	@JsonView(Scope.Search.class)
	@Column(name="PRODUCT_NAME")
	private String productName;

	@JsonView(Scope.Form.class)
	@OneToMany(mappedBy = "product")
	private List<Ingredient> ingredientList;

	@JsonView(Scope.Form.class)
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_GROUP")
	private List<ReferenceLookUp> productGroupList;

	@JsonView(Scope.Form.class)
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="PROMO_GROUP")
	private List<ReferenceLookUp> promoGroup;

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
	public List<ReferenceLookUp> getPromoGroup() {
		return promoGroup;
	}
	public void setPromoGroup(List<ReferenceLookUp> promoGroup) {
		this.promoGroup = promoGroup;
	}
}
