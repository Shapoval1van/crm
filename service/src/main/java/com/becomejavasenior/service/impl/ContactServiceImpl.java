package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.filter.ContactFilter;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.service.ContactService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 * Modified by Timue Aminev, tim8917@gmail.com
 */
public class ContactServiceImpl implements ContactService {

    private ContactDao contactDao;

    public ContactServiceImpl() {
        this.contactDao = new DaoFactoryImpl().getContactDao();
    }

    @Override
    public int create(Contact contact) throws DatabaseException {
        return contactDao.create(contact);
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        return contactDao.getContactById(id);
    }

    @Override
    public ArrayList<Company> getContactsByFilter(ContactFilter contactFilter) {
        return null;
    }

    @Override
    public int update(Contact contact) throws DatabaseException {
        return contactDao.update(contact);
    }

    @Override
    public int delete(Contact contact) throws DatabaseException {
        return contactDao.delete(contact);
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        return contactDao.findAll();
    }

    @Override
    public int getCount() throws DatabaseException {
        return contactDao.getCount();
    }

    @Override
    public int createContactForDeal(int dealId, Contact contact) throws DatabaseException {
        return contactDao.createContactForDeal(dealId, contact);
    }
}
