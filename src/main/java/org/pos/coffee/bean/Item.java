package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonView;
import org.pos.coffee.web.json.Scope;

import javax.persistence.*;


@Entity
@Table(name="ITEM")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 2776590636632885067L;

	@JsonView(Scope.Search.class)
	@Column(name="ITEM_NAME")
	private String itemName;

	@JsonView(Scope.Search.class)
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="UOM_ID")
	@JsonView(Scope.Form.class)
	private ReferenceLookUp uom;
	
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
	
	
}
