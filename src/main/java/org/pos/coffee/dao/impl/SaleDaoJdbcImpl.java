package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.bean.helper.SaleOrderHelper;
import org.pos.coffee.dao.SaleDao;
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
 * Created by Laurie on 1/21/2016.
 */
@Repository("saleDaoJdbc")
public class SaleDaoJdbcImpl implements SaleDaoJdbc {

    @Autowired
    private DataSource dataSource;

    private static final StringBuilder SEARCH_SOLD_PRODUCT = new StringBuilder();

    static {
        SEARCH_SOLD_PRODUCT.append("SELECT SUM(OL.QUANTITY) AS QUANTITY, P.ID AS PRODUCT_ID, P.PRODUCT_NAME, LP.ID AS LIST_ID, LP.PRICE, ")
                .append("       PP.PRODUCT_NAME  AS PARENT_NAME, PP.ID AS PARENT_ID, SIZE.VALUE_ AS SIZE, SIZE.ID AS SIZE_ID, ")
                .append("       P_G.ID AS PRODUCT_GROUP_ID, P_G.GROUP_NAME ")
                .append("FROM   SALE S ")
                .append("       JOIN ORDER_LINE OL ON S.ID = OL.SALE_ID ")
                .append("       JOIN PRODUCT P ON OL.PRODUCT_ID = P.ID ")
                .append("       JOIN LIST_PRICE LP ON OL.LIST_PRICE = LP.ID ")
                .append("       LEFT JOIN PRODUCT PP ON P.PARENT = PP.ID ")
                .append("       LEFT JOIN REFERENCE_LOOKUP SIZE ON P.SIZE = SIZE.ID ")
                .append("       LEFT JOIN PRODUCT_GROUP PG ON P.ID = PG.PRODUCT_ID ")
                .append("       LEFT JOIN P_GROUP P_G ON PG.PGROUP = P_G.ID ")
                .append("WHERE  STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("       AND STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP  BY P.ID, ")
                .append("          OL.LIST_PRICE ");
    }

    public static RowMapper<SaleOrderHelper> SOLD_PRODUCT = new RowMapper<SaleOrderHelper>() {

        @Override
        public SaleOrderHelper mapRow(ResultSet rs, int rowNum) throws SQLException {
            final SaleOrderHelper saleOrderHelper = new SaleOrderHelper();
            saleOrderHelper.setQuantity(rs.getDouble("QUANTITY"));
            saleOrderHelper.setProductId(rs.getLong("PRODUCT_ID"));
            saleOrderHelper.setListPriceId(rs.getLong("LIST_ID"));
            saleOrderHelper.setPrice(rs.getDouble("PRICE"));
            saleOrderHelper.setProductName(rs.getString("PRODUCT_NAME"));
            saleOrderHelper.setParentId(rs.getLong("PARENT_ID"));
            saleOrderHelper.setParentName(rs.getString("PARENT_NAME"));
            saleOrderHelper.setSizeId(rs.getLong("SIZE_ID"));
            saleOrderHelper.setSizeName(rs.getString("SIZE"));
            saleOrderHelper.setProductGroupId(rs.getLong("PRODUCT_GROUP_ID"));
            saleOrderHelper.setProductName(rs.getString("GROUP_NAME"));

            return saleOrderHelper;
        }

    };

    @Override
    public List<SaleOrderHelper> getAllSales(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<SaleOrderHelper> result = template.query(SEARCH_SOLD_PRODUCT.toString(), params, SOLD_PRODUCT);
        return result;
    }
}
