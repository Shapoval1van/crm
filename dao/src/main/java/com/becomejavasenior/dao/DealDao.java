package com.becomejavasenior.dao;


import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;

import java.util.List;

public interface DealDao {
    int create(Deal deal) throws DatabaseException;
    Deal getDealById(int id) throws DatabaseException;
    int update(Deal deal) throws DatabaseException;
    int updateDealContact(Deal deal) throws DatabaseException;
    int getMaxId() throws DatabaseException;
    int delete(Deal deal) throws DatabaseException;
    List<Deal> findAll() throws DatabaseException;
    int countDealsWithTasks() throws DatabaseException;
    int countDealsWithoutTasks() throws DatabaseException;
    List<Deal> getDealsForContactById(Contact contact) throws DatabaseException;
    List<Deal> getDealsForCompanyById(Company company) throws DatabaseException;
    Deal getDealForTask(Task task) throws DatabaseException;
    int createDealForContact(int contactId, Deal deal) throws DatabaseException;

}
