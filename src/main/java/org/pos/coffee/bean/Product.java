package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.evey.annotation.JoinList;
import org.evey.annotation.UniqueField;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseEntity {

	@Column(name = "PRODUCT_CODE", unique = true, nullable = false)
	@UniqueField
	private String productCode;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="DESCRIPTION")
	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<Ingredient> ingredientList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_GROUP_ID", referencedColumnName = "ID")
	private ProductGroup productGroup;

	@Column(name = "PROD_GROUP_ID", insertable = false, updatable = false)
	private Long productGroupId;

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
	@JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
	private ReferenceLookUp category;

	@Column(name = "CATEGORY", insertable = false, updatable = false)
	private Long categoryId;

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

	private transient String displayProductGroup;

	private transient String displayPromoGroup;

	private transient String displayProductGroupList;

	private transient String displayPromoGroupList;


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

	public void setDisplayProductGroup(String displayProductGroup) {
		this.displayProductGroup = displayProductGroup;
	}

	public String getDisplayPromoGroup() {
		if(this.promoGroupList!=null){
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
		return displayPromoGroup;
	}

	public void setDisplayPromoGroup(String displayPromoGroup) {
		this.displayPromoGroup = displayPromoGroup;
	}

	public void setDisplayProductGroupList(String displayProductGroupList) {
		this.displayProductGroupList = displayProductGroupList;
	}

	public String getDisplayPromoGroupList() {
		if(this.promoGroupList!=null){
			StringBuffer buffer = new StringBuffer();
			for(ReferenceLookUp group: this.promoGroupList){
				buffer.append(group.getId()+"|");
			}
			String value = buffer.toString();
			return value.substring(0, value.length() > 0 ? value.length() - 1 : value.length());
		}
		return displayPromoGroupList;
	}

	public void setDisplayPromoGroupList(String displayPromoGroupList) {
		this.displayPromoGroupList = displayPromoGroupList;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ReferenceLookUp getCategory() {
		return category;
	}

	public void setCategory(ReferenceLookUp category) {
		this.category = category;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public String getDisplayProductGroup() {
		return displayProductGroup;
	}

	public String getDisplayProductGroupList() {
		return displayProductGroupList;
	}

	public Long getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(Long productGroupId) {
		this.productGroupId = productGroupId;
	}
}
