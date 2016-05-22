package org.pos.coffee.dao.impl;

import org.pos.coffee.dao.PurchaseOrderDaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurie on 5/22/2016.
 */
@Repository("purchaseOrderJdbc")
public class PurchaseOrderDaoJdbcImpl implements PurchaseOrderDaoJdbc {

    @Autowired
    private DataSource dataSource;

    private static final StringBuilder GET_PENDING_PURCHASES = new StringBuilder();

    static {
        GET_PENDING_PURCHASES.append("SELECT PURCHASE_CODE, STATUS FROM purchase where STATUS NOT IN ('Received','Cancelled');");
    }

    @Override
    public Map getPendingPurchases() {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        SqlRowSet pendingPurchases = template.queryForRowSet(GET_PENDING_PURCHASES.toString(), params);

        Map pendingPurchasesMap = new HashMap();

        while (pendingPurchases.next()) {
            pendingPurchasesMap.put(pendingPurchases.getString("PURCHASE_CODE"), pendingPurchases.getString("STATUS"));
        }

        return pendingPurchasesMap;
    }
}
