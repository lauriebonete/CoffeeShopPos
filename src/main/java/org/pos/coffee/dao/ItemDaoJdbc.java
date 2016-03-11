package org.pos.coffee.dao;

import org.pos.coffee.bean.helper.report.ConsumptionHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 3/10/2016.
 */
public interface ItemDaoJdbc {
    public List<ConsumptionHelper> oountConsumedItem(Date startDate, Date endDate);
}
