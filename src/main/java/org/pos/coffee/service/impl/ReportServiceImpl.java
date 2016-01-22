package org.pos.coffee.service.impl;

import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.SaleOrderHelper;
import org.pos.coffee.dao.impl.SaleDaoJdbc;
import org.pos.coffee.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Laurie on 1/21/2016.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier("saleDaoJdbc")
    private SaleDaoJdbc saleDaoJdbc;

    @Override
    public List<SaleOrderHelper> prepareSalesData(Date startDate, Date endDate) {
        return saleDaoJdbc.getAllSales(startDate, endDate);
    }

    @Override
    public void buildReportData(List<SaleOrderHelper> saleOrderHelpers, List<ReferenceLookUp> categoryList, List<ProductGroup> productGroups) {
        Map<Long,Map<Long, List>> categoryGroup = new HashMap<>();
        Map<Long,List<SaleOrderHelper>> productGroup = new HashMap<>();
        for(SaleOrderHelper saleOrderHelper: saleOrderHelpers){
            if(!productGroup.containsKey(saleOrderHelper.getProductGroupId())){
                List<SaleOrderHelper> saleOrderHelperList = new ArrayList<>();
                saleOrderHelperList.add(saleOrderHelper);
                productGroup.put(saleOrderHelper.getProductGroupId(),saleOrderHelperList);
            } else {
                productGroup.get(saleOrderHelper.getProductGroupId()).add(saleOrderHelper);
            }
        }
        productGroup.isEmpty();
    }
}
