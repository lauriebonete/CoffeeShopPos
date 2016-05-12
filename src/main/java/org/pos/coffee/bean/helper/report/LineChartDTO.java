package org.pos.coffee.bean.helper.report;

/**
 * Created by Laurie on 5/8/2016.
 */
public class LineChartDTO {
    private String labelName;
    private Double saleTotal;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Double saleTotal) {
        this.saleTotal = saleTotal;
    }
}