package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="ITEM")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 2776590636632885067L;

	@Column(name="ITEM_CODE", unique = true, nullable = false)
	private String itemCode;

	@Column(name="ITEM_NAME")
	private String itemName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="UOM")
	private ReferenceLookUp uom;

	@Column(name = "UOM", insertable = false, updatable = false)
	private Long uomId;

	@Column(name="CRITICAL_LEVEL")
	private Double criticalLevel;
	
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Double getCriticalLevel() {
		return criticalLevel;
	}

	public void setCriticalLevel(Double criticalLevel) {
		this.criticalLevel = criticalLevel;
	}
}
