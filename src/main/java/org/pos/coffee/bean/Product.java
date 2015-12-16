package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.evey.annotation.JoinList;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseEntity {

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="DESCRIPTION")
	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
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

	@Column(name = "IMAGE", insertable = false, updatable = false)
	private Long productImageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SIZE", referencedColumnName = "ID")
	private ReferenceLookUp size;

	@Column(name = "SIZE", insertable = false, updatable = false)
	private Long sizeId;

	@JsonManagedReference
	@OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
	private ListPrice price;

	@Column(name = "SHOW_PRODUCT")
	private Boolean isDisplayOnOrder;

	@ManyToOne
	@JoinColumn(name = "PARENT", referencedColumnName = "ID")
	@JsonBackReference
	private Product parentProduct;

	@Column(name = "PARENT", insertable = false, updatable = false)
	private Long parentProductId;

	@JsonManagedReference
	@OneToMany(mappedBy = "parentProduct", fetch = FetchType.LAZY)
	private List<Product> productUnder;


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

	public Long getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(Long productImageId) {
		this.productImageId = productImageId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonView
	public String displayProductGroup(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.productGroupList){
			buffer.append(group.getValue()+", ");
		}
		String value = buffer.toString();
		if(value.length()>0){
			return value.substring(0, value.length()-2);
		}
		return "";
	}

	@JsonView
	public String displayPromoGroup(){
		StringBuffer buffer = new StringBuffer();
		for(ReferenceLookUp group: this.promoGroupList){
			buffer.append(group.getValue()+", ");
		}
		String value = buffer.toString();
		if(value.length()>0){
			return value.substring(0, value.length()-2);
		}
		return "";
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

	public ReferenceLookUp getSize() {
		return size;
	}

	public void setSize(ReferenceLookUp size) {
		this.size = size;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Boolean getIsDisplayOnOrder() {
		return isDisplayOnOrder;
	}

	public void setIsDisplayOnOrder(Boolean isDisplayOnOrder) {
		this.isDisplayOnOrder = isDisplayOnOrder;
	}

	public Product getParentProduct() {
		return parentProduct;
	}

	public void setParentProduct(Product parentProduct) {
		this.parentProduct = parentProduct;
	}

	public Long getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(Long parentProductId) {
		this.parentProductId = parentProductId;
	}

	public List<Product> getProductUnder() {
		return productUnder;
	}

	public void setProductUnder(List<Product> productUnder) {
		this.productUnder = productUnder;
	}

	public ListPrice getPrice() {
		return price;
	}

	public void setPrice(ListPrice price) {
		this.price = price;
	}
}
