package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.bean.helper.report.PurchaseReportHelper;
import org.pos.coffee.dao.PurchaseDaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 2/8/2016.
 */

@Repository("purchaseDaoJdbc")
public class PurchaseDaoJdbcImpl implements PurchaseDaoJdbc {

    @Autowired
    private DataSource dataSource;

    private static final StringBuilder GET_PURCHASE = new StringBuilder();

    static {
        GET_PURCHASE.append("SELECT SUM(PO.QTY_RECEIVE) AS RECEIVED,SUM(PO.PRICE) AS PRICE,I.ITEM_CODE AS ITEM_CODE,I.ITEM_NAME AS ITEM_NAME, R.VALUE_ AS UOM ")
                .append("FROM PURCHASE P ")
                .append("JOIN PURCHASE_ORDER PO ON P.ID = PO.PURCHASE_ID ")
                .append("JOIN ITEM I ON PO.ITEM_ID = I.ID ")
                .append("JOIN REFERENCE_LOOKUP R ON I.UOM = R.ID ")
                .append("WHERE STR_TO_DATE(P.RECEIVE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(P.RECEIVE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP BY ITEM_ID ");

    }

    @Override
    public List<PurchaseReportHelper> getPurchaseUsingDateRange(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<PurchaseReportHelper> purchaseReportHelperList = template.query(GET_PURCHASE.toString(), params, new RowMapper<PurchaseReportHelper>() {

            @Override
            public PurchaseReportHelper mapRow(ResultSet resultSet, int i) throws SQLException {
                final PurchaseReportHelper purchaseReportHelper = new PurchaseReportHelper();
                purchaseReportHelper.setQtyReceived(resultSet.getDouble("RECEIVED"));
                purchaseReportHelper.setPrice(resultSet.getDouble("PRICE"));
                purchaseReportHelper.setItemCode(resultSet.getString("ITEM_CODE"));
                purchaseReportHelper.setItemName(resultSet.getString("ITEM_NAME"));
                purchaseReportHelper.setUom(resultSet.getString("UOM"));
                return purchaseReportHelper;
            }
        });

        return purchaseReportHelperList;
    }
}
