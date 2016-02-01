package org.pos.coffee.bean.helper.report;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 2/1/2016.
 */
public class MonthlySaleCategoryHelper {
    private List<Double> categorySale;
    private Double totalSales;
    private Double discount;
    private Double surcharge;
    private List<Double> saleBalance;
    private Double tax;
    private Date date;

    public List<Double> getCategorySale() {
        return categorySale;
    }

    public void setCategorySale(List<Double> categorySale) {
        this.categorySale = categorySale;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public List<Double> getSaleBalance() {
        return saleBalance;
    }

    public void setSaleBalance(List<Double> saleBalance) {
        this.saleBalance = saleBalance;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
