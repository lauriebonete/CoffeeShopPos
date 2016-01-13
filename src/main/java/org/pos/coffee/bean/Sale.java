package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.evey.annotation.JoinList;
import org.evey.annotation.UniqueField;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SALE")
public class Sale extends BaseEntity {

	@Column(name = "SALE_CODE", nullable = false, unique = true)
	@UniqueField
	private String saleCode;

	@OneToMany(mappedBy = "sale")
	@JsonManagedReference
	private List<Order> orders;

	@ManyToOne
	@JoinColumn(name = "SERVER", referencedColumnName = "ID")
	private MaestroUser server;

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

	@ManyToOne
	@JoinColumn(name="CUSTOMER", referencedColumnName = "ID")
	private Customer customer;

	@Column(name = "CUSTOMER", insertable = false, updatable = false)
	private Long customerId;

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
	public MaestroUser getServer() {
		return server;
	}
	public void setServer(MaestroUser server) {
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}
}
