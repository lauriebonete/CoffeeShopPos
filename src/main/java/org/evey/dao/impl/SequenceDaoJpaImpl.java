package org.evey.dao.impl;

import org.evey.bean.Sequence;
import org.evey.dao.SequenceDao;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

/**
 * Created by Laurie on 1/7/2016.
 */
@Repository("sequenceDao")
public class SequenceDaoJpaImpl extends BaseEntityDaoJpaImpl<Sequence,Long> implements SequenceDao {

    @Override
    public Long incrementValue(String key, int increment, int retryCount, int maxRetry) {
        Sequence sequence = loadSequenceByKey(key);
        if(sequence==null){
            sequence = new Sequence();
            sequence.setKey(key);
            sequence.setValue(0L);
        }
        sequence.incrementValue(increment);
        try{
            this.save(sequence);
        } catch (OptimisticLockException | StaleObjectStateException e){
            if(retryCount>maxRetry){
                throw new RuntimeException(
                        "Maximum retry count reached while generating sequence number for " + key);
            } else {
                int currentRetryCount = retryCount + 1;
                return incrementValue(key, increment, currentRetryCount, maxRetry);
            }
        }
        return sequence.getValue();
    }

    @Override
    public Sequence loadSequenceByKey(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT obj FROM Sequence obj where obj.key = :key ");
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("key", key);

        try {
            Sequence sequenceFound = (Sequence) query.getSingleResult();
            return sequenceFound;
        } catch (NoResultException nre){
            return null;
        }
    }
}
