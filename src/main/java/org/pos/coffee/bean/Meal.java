package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="MEAL")
public class Meal extends BaseEntity {

	@Column(name="MEAL_NAME")
	private String mealName;

	@OneToMany(mappedBy = "product")
	private List<ProductMeal> productMealList;

	@Column(name="IS_CUSTOMIZABLE")
	private Boolean isCustomizable;

	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public List<ProductMeal> getProductMealList() {
		return productMealList;
	}
	public void setProductMealList(List<ProductMeal> productMealList) {
		this.productMealList = productMealList;
	}
	public Boolean getIsCustomizable() {
		return isCustomizable;
	}
	public void setIsCustomizable(Boolean isCustomizable) {
		this.isCustomizable = isCustomizable;
	}
	
}
