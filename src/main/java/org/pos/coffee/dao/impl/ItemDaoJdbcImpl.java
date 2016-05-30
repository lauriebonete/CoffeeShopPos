package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.ConsumptionHelper;
import org.pos.coffee.bean.helper.report.PurchaseReportHelper;
import org.pos.coffee.dao.ItemDaoJdbc;
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
 * Created by Laurie on 3/10/2016.
 */
@Repository("itemDaoJdbc")
public class ItemDaoJdbcImpl implements ItemDaoJdbc {

    @Autowired
    private DataSource dataSource;

    private static final StringBuilder GET_CONSUMPTION = new StringBuilder();

    static {
        GET_CONSUMPTION.append("SELECT IT.ITEM_CODE AS ITEM_CODE, IT.ITEM_NAME AS ITEM_NAME, CONCAT(CONCAT(SUM(I.QUANTITY*OL.QUANTITY),' '),UOM.VALUE_) AS CONSUMED ")
                .append("FROM SALE S ")
                .append("JOIN ORDER_LINE OL ON S.ID = OL.SALE_ID ")
                .append("JOIN PRODUCT P ON OL.PRODUCT_ID = P.ID ")
                .append("JOIN INGREDIENT I ON P.ID = I.PRODUCT_ID ")
                .append("JOIN ITEM IT ON I.ITEM = IT.ID ")
                .append("JOIN REFERENCE_LOOKUP UOM ON IT.UOM = UOM.ID ")
                .append("WHERE DATE_FORMAT(SALE_DATE,'%Y-%m-%d') >= DATE_FORMAT(:START_DATE,'%Y-%m-%d') ")
                .append("AND DATE_FORMAT(SALE_DATE,'%Y-%m-%d') <= DATE_FORMAT(:END_DATE,'%Y-%m-%d') ")
                .append("GROUP BY IT.ID ");

    }

    @Override
    public List<ConsumptionHelper> oountConsumedItem(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<ConsumptionHelper> consumptionHelperList = template.query(GET_CONSUMPTION.toString(), params, new RowMapper<ConsumptionHelper>() {

            @Override
            public ConsumptionHelper mapRow(ResultSet resultSet, int i) throws SQLException {
                final ConsumptionHelper consumptionHelper = new ConsumptionHelper();
                consumptionHelper.setItemCode(resultSet.getString("ITEM_CODE"));
                consumptionHelper.setItemName(resultSet.getString("ITEM_NAME"));
                consumptionHelper.setConsumed(resultSet.getString("CONSUMED"));
                return consumptionHelper;
            }
        });
        return consumptionHelperList;
    }
}
