package org.evey.dao;

import org.evey.bean.Sequence;

/**
 * Created by Laurie on 1/7/2016.
 */
public interface SequenceDao extends BaseEntityDao<Sequence,Long> {
    public Long incrementValue(String key, int increment, int retryCount, int maxRetry);
    public Sequence loadSequenceByKey(String key);
}
