package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.SaleOrderHelper;
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
    private static final StringBuilder GET_TOTAL_SALE = new StringBuilder();
    private static final StringBuilder GET_CATEGORY_SALE = new StringBuilder();
    private static final StringBuilder GET_SURDISTAX_SALE = new StringBuilder();

    static {
        SEARCH_SOLD_PRODUCT.append("SELECT SUM(OL.QUANTITY) AS QUANTITY, P.ID AS PRODUCT_ID, P.PRODUCT_NAME, LP.ID AS LIST_ID, LP.PRICE, ")
                .append("       PP.PRODUCT_NAME  AS PARENT_NAME, PP.ID AS PARENT_ID, SIZE.VALUE_ AS SIZE, SIZE.ID AS SIZE_ID, P.CATEGORY AS CATEGORY_ID ")
                .append("FROM   SALE S ")
                .append("       JOIN ORDER_LINE OL ON S.ID = OL.SALE_ID ")
                .append("       JOIN PRODUCT P ON OL.PRODUCT_ID = P.ID ")
                .append("       JOIN LIST_PRICE LP ON OL.LIST_PRICE = LP.ID ")
                .append("       LEFT JOIN PRODUCT PP ON P.PARENT = PP.ID ")
                .append("       LEFT JOIN REFERENCE_LOOKUP SIZE ON P.SIZE = SIZE.ID ")
                .append("WHERE  STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("       AND STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP  BY P.ID, ")
                .append("          OL.LIST_PRICE ");

        GET_TOTAL_SALE.append("SELECT IFNULL(SUM(TOTAL_SALE),0) FROM SALE ")
                .append("WHERE  STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d')  ")
                .append("AND STR_TO_DATE(DATE(SALE_DATE), '%Y-%m-%d') <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ");


        GET_CATEGORY_SALE.append("SELECT IFNULL(SALE.TOTAL_SALE,0) FROM   REFERENCE_LOOKUP R ")
                .append("       LEFT JOIN (SELECT CATEGORY, SUM(TOTAL_SALE) AS TOTAL_SALE ")
                .append("                  FROM   SALE S ")
                .append("                         JOIN ORDER_LINE OL ON S.ID = OL.SALE_ID ")
                .append("                         JOIN PRODUCT P ON OL.PRODUCT_ID = P.ID ")
                .append("                         JOIN P_GROUP PG ON P.PROD_GROUP_ID = PG.ID ")
                .append("                  WHERE  ( S.SALE_DATE IS NULL ")
                .append("                            OR ( STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("                                 AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ) )) ")
                .append("                 AS SALE ON R.ID = SALE.CATEGORY ")
                .append("WHERE  R.CATEGORY_ = 'CATEGORY_PROD_CATEGORY' ")
                .append("ORDER BY R.ID ");

        GET_SURDISTAX_SALE.append("SELECT IFNULL(SUM(S.TOTAL_SURCHARGE),0) AS TOTAL_SURCHARGE, ")
                .append("  IFNULL(SUM(S.TAX),0) AS TAX, ")
                .append("  IFNULL(SUM(S.TOTAL_DISCOUNT),0) AS TOTAL_DISCOUNT ")
                .append("FROM SALE S ")
                .append("WHERE STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ");


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
            saleOrderHelper.setCategoryId(rs.getLong("CATEGORY_ID"));

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

    @Override
    public Double getTotalSaleForDate(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        Double result = template.queryForObject(GET_TOTAL_SALE.toString(), params, Double.class);
        return result;
    }

    @Override
    public List<Double> getSalesPerCategory(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Double> categorySale =  template.queryForList(GET_CATEGORY_SALE.toString(), params, Double.class);
        return categorySale;
    }

    @Override
    public List<Map<String, Double>> getDisSurTax(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Map<String, Double>> results = template.query(GET_SURDISTAX_SALE.toString(), params, new RowMapper<Map<String, Double>>() {

            @Override
            public Map<String, Double> mapRow(ResultSet resultSet, int i) throws SQLException {
                Map<String,Double> map = new HashMap<String, Double>();
                map.put("surcharge",resultSet.getDouble("TOTAL_SURCHARGE"));
                map.put("tax",resultSet.getDouble("TAX"));
                map.put("discount",resultSet.getDouble("TOTAL_DISCOUNT"));
                return map;
            }
        });

        return results;
    }
}
