package org.pos.coffee.bean;

import org.evey.annotation.JoinList;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SALE")
public class Sale extends BaseEntity {

	@OneToMany(mappedBy = "sale")
	private List<Order> orders;

	@ManyToOne
	@JoinColumn(name = "SERVER", referencedColumnName = "ID")
	private User server;

	@Column(name = "SERVER", insertable = false, updatable = false)
	private Long serverId;

	@ManyToOne
	@JoinColumn(name = "BRANCH", referencedColumnName = "KEY_")
	private ReferenceLookUp branch;

	@Column(name="SALE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date saleDate;

	@Column(name="TOTAL_SALE")
	private Double totalSale;

	@Column(name="TOTAL_COST")
	private Double totalCost;

	@Column(name="TOTAL_DISCOUNT")
	private Double totalDiscount;

	@Column(name="TOTAL_SURCHARGE")
	private Double totalSurcharge;

	@Column(name="TAX")
	private Double tax;

	@Column(name="PRE_TAX")
	private Double preTax;

	@JoinList
	@ManyToMany
	@JoinTable(name="SALE_PRICE_ADJST",
			joinColumns = {@JoinColumn(name="SALE_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="PRICE_SET_ID", referencedColumnName = "ID")
	)
	private List<PriceSet> appliedPriceSet;

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public User getServer() {
		return server;
	}
	public void setServer(User server) {
		this.server = server;
	}
	public ReferenceLookUp getBranch() {
		return branch;
	}
	public void setBranch(ReferenceLookUp branch) {
		this.branch = branch;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public Double getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(Double totalSale) {
		this.totalSale = totalSale;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public Double getTotalSurcharge() {
		return totalSurcharge;
	}
	public void setTotalSurcharge(Double totalSurcharge) {
		this.totalSurcharge = totalSurcharge;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public List<PriceSet> getAppliedPriceSet() {
		return appliedPriceSet;
	}

	public void setAppliedPriceSet(List<PriceSet> appliedPriceSet) {
		this.appliedPriceSet = appliedPriceSet;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getPreTax() {
		return preTax;
	}

	public void setPreTax(Double preTax) {
		this.preTax = preTax;
	}
}
