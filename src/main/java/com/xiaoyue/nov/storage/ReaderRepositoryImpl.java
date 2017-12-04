package com.xiaoyue.nov.storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by xiaoyue26 on 17/12/4.
 */
public class ReaderRepositoryImpl implements ReaderSweeper {
    @PersistenceContext
    private EntityManager em;


    @Override
    public int eliteSweep() {
        String update = "update reader" +
                " set fullname='elite'" +
                " where username='walt' ";
        return em.createQuery(update).executeUpdate();
    }
}
