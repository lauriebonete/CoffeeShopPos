package org.pos.coffee.bean;

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

	@ManyToOne
	@JoinColumn(name="CUSTOMER", referencedColumnName = "ID")
	private Customer customer;

	@Column(name = "CUSTOMER", insertable = false, updatable = false)
	private Long customerId;

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
}
