package org.pos.coffee.bean;

import javax.persistence.*;

@Entity
@Table(name="ITEM")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 2776590636632885067L;
	
	@Column(name="ITEM_NAME")
	private String itemName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="UOM")
	private ReferenceLookUp uom;

	@Column(name = "UOM", insertable = false, updatable = false)
	private Long uomId;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ReferenceLookUp getUom() {
		return uom;
	}
	public void setUom(ReferenceLookUp uom) {
		this.uom = uom;
	}

	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
}
