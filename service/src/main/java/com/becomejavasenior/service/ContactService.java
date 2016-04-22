package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.filter.ContactFilter;
import com.becomejavasenior.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public interface ContactService {

    int create(Contact contact) throws DatabaseException;

    Contact getContactById(int id) throws DatabaseException;

    ArrayList<Company> getContactsByFilter(ContactFilter contactFilter);

    int update(Contact contact) throws DatabaseException;

    int delete(Contact contact) throws DatabaseException;

    List<Contact> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;

    int createContactForDeal(int dealId, Contact contact) throws DatabaseException;

}
