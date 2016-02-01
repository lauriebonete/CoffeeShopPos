package org.pos.coffee.bean.helper.report;

import java.util.List;

/**
 * Created by Laurie on 2/1/2016.
 */
public class MonthlySaleHelper {
    private List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList;
    private List<String> headers;

    public List<MonthlySaleCategoryHelper> getMonthlySaleCategoryHelperList() {
        return monthlySaleCategoryHelperList;
    }

    public void setMonthlySaleCategoryHelperList(List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList) {
        this.monthlySaleCategoryHelperList = monthlySaleCategoryHelperList;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
