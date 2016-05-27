package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.PendingPurchaseDTO;
import org.pos.coffee.dao.PurchaseOrderDaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
        GET_PENDING_PURCHASES.append("SELECT PURCHASE_CODE, STATUS FROM purchase where STATUS NOT IN ('Received','Cancelled') ORDER BY ID ASC LIMIT 5;");
    }

    @Override
    public List<PendingPurchaseDTO> getPendingPurchases() {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        final Map<String, Object> params = new HashMap<>();
        List<PendingPurchaseDTO> results = template.query(GET_PENDING_PURCHASES.toString(), params, new RowMapper<PendingPurchaseDTO>() {
            @Override
            public PendingPurchaseDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                PendingPurchaseDTO pendingPurchaseDTO = new PendingPurchaseDTO();
                pendingPurchaseDTO.setPurchaseCode(resultSet.getString("PURCHASE_CODE"));
                pendingPurchaseDTO.setStatus(resultSet.getString("STATUS"));
                return pendingPurchaseDTO;
            }
        });

        return results;
    }
}
