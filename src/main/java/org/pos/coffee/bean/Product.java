package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.pos.coffee.annotation.JoinList;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public final class Product extends BaseEntity {

	@Column(name="PRODUCT_NAME")
	private String productName;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Ingredient> ingredientList;

	@JoinList
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="PRODUCT_GROUP",
			joinColumns = {@JoinColumn(name="PRODUCT_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="P_GROUP", referencedColumnName = "ID")
	)
	private List<ReferenceLookUp> productGroupList;

	@JoinList
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="PROMO_GROUP",
			joinColumns = {@JoinColumn(name="PRODUCT_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="P_GROUP", referencedColumnName = "ID")
	)
	private List<ReferenceLookUp> promoGroupList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE", referencedColumnName = "ID")
	private FileDetail productImage;

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

	public List<ReferenceLookUp> getPromoGroupList() {
		return promoGroupList;
	}

	public void setPromoGroupList(List<ReferenceLookUp> promoGroupList) {
		this.promoGroupList = promoGroupList;
	}

	public FileDetail getProductImage() {
		return productImage;
	}

	public void setProductImage(FileDetail productImage) {
		this.productImage = productImage;
	}

	@JsonView
	public String displayProductGroup(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.productGroupList){
			buffer.append(group.getValue()+", ");
		}
		String value = buffer.toString();
		return value.substring(0, value.length()>0 ? value.length()-2:value.length());
	}

	@JsonView
	public String displayPromoGroup(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.promoGroupList){
			buffer.append(group.getValue()+", ");
		}
		String value = buffer.toString();
		return value.substring(0, value.length() > 0 ? value.length() - 2 : value.length());
	}


	@JsonView
	public String displayProductGroupList(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.productGroupList){
			buffer.append(group.getId()+"|");
		}
		String value = buffer.toString();
		return value.substring(0, value.length() > 0 ? value.length() - 1 : value.length());
	}

	@JsonView
	public String displayPromoGroupList(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.promoGroupList){
			buffer.append(group.getId()+"|");
		}
		String value = buffer.toString();
		return value.substring(0, value.length() > 0 ? value.length() - 1 : value.length());
	}
}
