package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.evey.annotation.JoinList;
import org.evey.annotation.UniqueField;
import org.evey.bean.BaseEntity;
import org.evey.bean.FileDetail;
import org.evey.bean.ReferenceLookUp;
import org.evey.bean.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private User server;

	@Column(name = "SERVER", insertable = false, updatable = false)
	private Long serverId;

	@ManyToOne
	@JoinColumn(name = "BRANCH", referencedColumnName = "KEY_")
	private ReferenceLookUp branch;

	@Column(name="SALE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
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

	@Column(name = "GROSS_LINE_PRICE")
	private Double grossTotalLinePrice;

	@Column(name = "TAX_RATE")
	private String taxRate;

	@JoinList
	@ManyToMany
	@JoinTable(name="SALE_PRICE_ADJST",
			joinColumns = {@JoinColumn(name="SALE_ID", referencedColumnName = "ID")},
			inverseJoinColumns = @JoinColumn(name="PRICE_SET_ID", referencedColumnName = "ID")
	)
	private List<PriceSet> appliedPriceSet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="RECEIPT_ID", referencedColumnName = "ID")
	private FileDetail receipt;

	@Column(name = "RECEIPT_ID", insertable = false, updatable = false)
	private Long receiptId;

	private transient String displaySaleDate;

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

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public Double getGrossTotalLinePrice() {
		return grossTotalLinePrice;
	}

	public void setGrossTotalLinePrice(Double grossTotalLinePrice) {
		this.grossTotalLinePrice = grossTotalLinePrice;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public FileDetail getReceipt() {
		return receipt;
	}

	public void setReceipt(FileDetail receipt) {
		this.receipt = receipt;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	@Override
	public Map<String, String> getOrderBy() {
		Map<String, String> orderMap = new HashMap<>();
		orderMap.put("id","DESC");
		return orderMap;
	}

	public String getDisplaySaleDate() {
		if(this.saleDate!=null){
			return new SimpleDateFormat("MM/dd/yyyy '-' hh:mma").format(this.saleDate);
		}
		return displaySaleDate;
	}
}
