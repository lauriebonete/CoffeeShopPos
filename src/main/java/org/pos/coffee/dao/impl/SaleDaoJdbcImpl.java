package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.dao.SaleDaoJdbc;
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

    private static final StringBuilder GET_TOTAL_SALE = new StringBuilder();
    private static final StringBuilder GET_CATEGORY_SALE = new StringBuilder();
    private static final StringBuilder GET_SURDISTAX_SALE = new StringBuilder();
    private static final StringBuilder GET_PRODUCT_SALE = new StringBuilder();
    private static final StringBuilder GET_PRODUCT_EXPENSE = new StringBuilder();
    private static final StringBuilder GET_SALE_MONTH = new StringBuilder();
    private static final StringBuilder GET_SALE_WEEK = new StringBuilder();
    private static final StringBuilder GET_SALE_DAY = new StringBuilder();
    private static final StringBuilder GET_SALE_COUNT_DAY = new StringBuilder();
    private static final StringBuilder GET_CATEGORY_PERCENTAGE = new StringBuilder();

    static {
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
                .append("                                 AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ) ) ")
                .append("                   GROUP BY CATEGORY) ")
                .append("                 AS SALE ON R.ID = SALE.CATEGORY ")
                .append("WHERE  R.CATEGORY_ = 'CATEGORY_PROD_CATEGORY' ")
                .append("ORDER BY R.ID ");

        GET_SURDISTAX_SALE.append("SELECT IFNULL(SUM(S.TOTAL_SURCHARGE),0) AS TOTAL_SURCHARGE, ")
                .append("  IFNULL(SUM(S.TAX),0) AS TAX, ")
                .append("  IFNULL(SUM(S.TOTAL_DISCOUNT),0) AS TOTAL_DISCOUNT ")
                .append("FROM SALE S ")
                .append("WHERE STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ");

        GET_PRODUCT_SALE.append("SELECT R.ID AS CATEGORY, R.VALUE_ AS CATEGORY_NAME, PG.ID AS GROUP_ID, ")
                .append("  PG.GROUP_NAME  AS GROUP_NAME, P.ID AS PARENT_ID, P.PRODUCT_NAME AS PARENT_NAME, ")
                .append("  C.ID AS CHILD_ID, C.PRODUCT_NAME AS CHILD_NAME, IFNULL(S.QUANTITY,0) AS QUANTITY, IFNULL(LP.PRICE,0) AS PRICE, IFNULL(S.LINE_PRICE,0) AS LINE_PRICE ")
                .append("FROM REFERENCE_LOOKUP R ")
                .append("LEFT JOIN P_GROUP PG ON PG.CATEGORY = R.ID ")
                .append("LEFT JOIN PRODUCT P ON P.PROD_GROUP_ID = PG.ID ")
                .append("LEFT JOIN PRODUCT C ON P.ID = C.PARENT ")
                .append("LEFT JOIN LIST_PRICE LP ON (LP.PRODUCT = P.ID OR LP.PRODUCT  = C.ID) ")
                .append("LEFT JOIN ")
                .append("  (SELECT OL.PRODUCT_ID AS PRODUCT_ID, SUM(OL.QUANTITY) AS QUANTITY, SUM(OL.LINE_PRICE) AS LINE_PRICE ")
                .append("  FROM SALE S ")
                .append("  JOIN ORDER_LINE OL ON OL.SALE_ID = S.ID ")
                .append("  WHERE STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("  AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("  GROUP BY OL.PRODUCT_ID ")
                .append("  ) AS S ON S.PRODUCT_ID                      = C.ID ")
                .append("WHERE R.CATEGORY_                             = 'CATEGORY_PROD_CATEGORY' ")
                .append("AND P.SHOW_PRODUCT                            = 1 ")
                .append("ORDER BY R.ID, PG.ID, P.ID ");

        GET_PRODUCT_EXPENSE.append("SELECT R.ID AS CATEGORY, R.VALUE_ AS CATEGORY_NAME, PG.ID AS GROUP_ID, PG.GROUP_NAME AS GROUP_NAME, ")
                .append("  P.ID AS PARENT_ID, P.PRODUCT_NAME AS PARENT_NAME, C.ID AS CHILD_ID, ")
                .append("  C.PRODUCT_NAME AS CHILD_NAME, IFNULL(S.QUANTITY,0) AS QUANTITY, IFNULL(S.LINE_EXPENSE,0) AS LINE_EXPENSE ")
                .append("FROM REFERENCE_LOOKUP R ")
                .append("LEFT JOIN P_GROUP PG ON PG.CATEGORY = R.ID ")
                .append("LEFT JOIN PRODUCT P ON P.PROD_GROUP_ID = PG.ID ")
                .append("LEFT JOIN PRODUCT C ON P.ID = C.PARENT ")
                .append("LEFT JOIN ")
                .append("  (SELECT OL.PRODUCT_ID  AS PRODUCT_ID, SUM(OL.QUANTITY) AS QUANTITY, SUM(OL.LINE_EXPENSE) AS LINE_EXPENSE ")
                .append("  FROM SALE S ")
                .append("  JOIN ORDER_LINE OL ON OL.SALE_ID = S.ID ")
                .append("  WHERE STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("  AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("  GROUP BY OL.PRODUCT_ID ")
                .append("  ) AS S ON S.PRODUCT_ID = C.ID ")
                .append("WHERE R.CATEGORY_        = 'CATEGORY_PROD_CATEGORY' ")
                .append("AND P.SHOW_PRODUCT       = 1 ")
                .append("ORDER BY R.ID, PG.ID, P.ID ");



        GET_SALE_MONTH.append("SELECT SUM(TOTAL_SALE) ")
                .append("FROM SALE ")
                .append("WHERE STR_TO_DATE(SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP BY DATE_FORMAT(SALE_DATE, '%Y-%m') ");

        GET_SALE_WEEK.append("SELECT SUM(TOTAL_SALE) ")
                .append("FROM SALE ")
                .append("WHERE STR_TO_DATE(SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP BY DATE_FORMAT(SALE_DATE, '%d') ");

        GET_SALE_DAY.append("SELECT SUM(TOTAL_SALE) ")
                .append("FROM SALE ")
                .append("WHERE STR_TO_DATE(SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP BY DATE_FORMAT(SALE_DATE, '%d') ");

        GET_SALE_COUNT_DAY.append("SELECT COUNT(TOTAL_SALE) ")
                .append("FROM SALE ")
                .append("WHERE STR_TO_DATE(SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("AND STR_TO_DATE(SALE_DATE, '%Y-%m-%d')   <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ")
                .append("GROUP BY DATE_FORMAT(SALE_DATE, '%d') ");

        GET_CATEGORY_PERCENTAGE.append("SELECT IFNULL(SALE.TOTAL_QUANTITY,0) AS TOTAL_QUANTITY, R.VALUE_ AS CATEGORY_NAME ")
                .append("FROM REFERENCE_LOOKUP R ")
                .append("LEFT JOIN ")
                .append("  (SELECT CATEGORY, ")
                .append("    SUM(OL.QUANTITY) AS TOTAL_QUANTITY ")
                .append("  FROM SALE S ")
                .append("  JOIN ORDER_LINE OL ON S.ID = OL.SALE_ID ")
                .append("  JOIN PRODUCT P ON OL.PRODUCT_ID = P.ID ")
                .append("  JOIN P_GROUP PG ON P.PROD_GROUP_ID = PG.ID ")
                .append("  WHERE ( S.SALE_DATE IS NULL ")
                .append("          OR ( STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d') >= STR_TO_DATE(:START_DATE, '%Y-%m-%d') ")
                .append("                AND STR_TO_DATE(S.SALE_DATE, '%Y-%m-%d')  <= STR_TO_DATE(:END_DATE, '%Y-%m-%d') ) ) ")
                .append("  GROUP BY CATEGORY ")
                .append("  ) AS SALE ON R.ID = SALE.CATEGORY ")
                .append("WHERE R.CATEGORY_   = 'CATEGORY_PROD_CATEGORY' ")
                .append("ORDER BY R.ID  ");
    }

    public static RowMapper<ProductSaleHelper> PRODUCT_HELPER = new RowMapper<ProductSaleHelper>() {
        @Override
        public ProductSaleHelper mapRow(ResultSet resultSet, int i) throws SQLException {
            final ProductSaleHelper productSaleHelper = new ProductSaleHelper();
            productSaleHelper.setCategoryId(resultSet.getLong("CATEGORY"));
            productSaleHelper.setCategoryName(resultSet.getString("CATEGORY_NAME"));
            productSaleHelper.setGroupId(resultSet.getLong("GROUP_ID"));
            productSaleHelper.setGroupName(resultSet.getString("GROUP_NAME"));
            productSaleHelper.setParentId(resultSet.getLong("PARENT_ID"));
            productSaleHelper.setParentName(resultSet.getString("PARENT_NAME"));
            productSaleHelper.setProductId(resultSet.getLong("CHILD_ID"));
            productSaleHelper.setProductName(resultSet.getString("CHILD_NAME"));
            productSaleHelper.setQuantity(resultSet.getDouble("QUANTITY"));
            productSaleHelper.setPrice(resultSet.getDouble("PRICE"));
            productSaleHelper.setLinePrice(resultSet.getDouble("LINE_PRICE"));
            return productSaleHelper;
        }
    };

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

    @Override
    public List<ProductSaleHelper> getProductSalePerDate(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<ProductSaleHelper> productSaleHelperList = template.query(GET_PRODUCT_SALE.toString(), params, PRODUCT_HELPER);
        return productSaleHelperList;
    }

    @Override
    public List<Double> getSalePerMonth(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Double> salePerMonth = template.queryForList(GET_SALE_MONTH.toString(),params,Double.class);
        return salePerMonth;
    }

    @Override
    public List<Double> getSalePerWeek(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Double> salePerWeek = template.queryForList(GET_SALE_WEEK.toString(),params,Double.class);
        return salePerWeek;
    }

    @Override
    public List<Double> getSalePerDay(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Double> salePerDay = template.queryForList(GET_SALE_WEEK.toString(),params,Double.class);
        return salePerDay;
    }

    @Override
    public List<Double> getSaleCountPerDay(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<Double> saleCountPerDay = template.queryForList(GET_SALE_COUNT_DAY.toString(),params,Double.class);
        return saleCountPerDay;
    }

    @Override
    public List<CategoryHelper> getCategoryPercentage(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<CategoryHelper> categoryHelperList = template.query(GET_CATEGORY_PERCENTAGE.toString(), params, new RowMapper<CategoryHelper>() {
            @Override
            public CategoryHelper mapRow(ResultSet resultSet, int i) throws SQLException {
                final CategoryHelper categoryHelper = new CategoryHelper();
                categoryHelper.setQuantity(resultSet.getDouble("TOTAL_QUANTITY"));
                categoryHelper.setCategoryName(resultSet.getString("CATEGORY_NAME"));
                return categoryHelper;
            }
        });

        return categoryHelperList;
    }

    @Override
    public List<ProductSaleHelper> getProductExpensePerDate(Date startDate, Date endDate) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        params.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        params.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        List<ProductSaleHelper> productSaleHelperList = template.query(GET_PRODUCT_EXPENSE.toString(), params, new RowMapper<ProductSaleHelper>() {
            @Override
            public ProductSaleHelper mapRow(ResultSet resultSet, int i) throws SQLException {
                final ProductSaleHelper productSaleHelper = new ProductSaleHelper();
                productSaleHelper.setCategoryId(resultSet.getLong("CATEGORY"));
                productSaleHelper.setCategoryName(resultSet.getString("CATEGORY_NAME"));
                productSaleHelper.setGroupId(resultSet.getLong("GROUP_ID"));
                productSaleHelper.setGroupName(resultSet.getString("GROUP_NAME"));
                productSaleHelper.setParentId(resultSet.getLong("PARENT_ID"));
                productSaleHelper.setParentName(resultSet.getString("PARENT_NAME"));
                productSaleHelper.setProductId(resultSet.getLong("CHILD_ID"));
                productSaleHelper.setProductName(resultSet.getString("CHILD_NAME"));
                productSaleHelper.setQuantity(resultSet.getDouble("QUANTITY"));
                productSaleHelper.setLinePrice(resultSet.getDouble("LINE_EXPENSE"));
                return productSaleHelper;
            }
        });
        return productSaleHelperList;
    }
}
