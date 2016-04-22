package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.DealService;

import java.util.*;

/**
 * Created by aivlev on 3/23/16.
 */
public class DealServiceImpl implements DealService {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";

    private DealDao dealDao;

    public DealServiceImpl() {
        this.dealDao = new DaoFactoryImpl().getDealDao();
    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        return dealDao.create(deal);
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        return dealDao.getDealById(id);
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        return dealDao.update(deal);
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        return dealDao.delete(deal);
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        return dealDao.findAll();
    }

    public Map<String, List<Deal>> filterSuccessAndFailedDeals(List<Deal> deals) {
        Map<String, List<Deal>> mapDeals = new HashMap<>();
        List<Deal> successDeals = new LinkedList<>();
        List<Deal> failedDeals = new LinkedList<>();
        ListIterator<Deal> listIterator = deals.listIterator();
        while(listIterator.hasNext()){
            Deal tempDeal = listIterator.next();
            if(tempDeal.getDealStage() == DealStage.SUCCESS){
                successDeals.add(tempDeal);
            }
            if(tempDeal.getDealStage() == DealStage.FAILED_AND_CLOSED){
                failedDeals.add(tempDeal);
            }
        }
        mapDeals.put(SUCCESS_DEALS, successDeals);
        mapDeals.put(FAILED_DEALS, failedDeals);
        return mapDeals;
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        return dealDao.countDealsWithTasks();
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        return dealDao.countDealsWithoutTasks();
    }

    @Override

    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        return dealDao.createDealForContact(contactId, deal);
    }

    public int updateDealContact(Deal deal) throws DatabaseException {
        return dealDao.updateDealContact(deal);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return dealDao.getMaxId();
    }

}
