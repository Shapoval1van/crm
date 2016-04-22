package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagService {
    int create(Tag tag)throws DatabaseException;
    Tag getTagById(int id)throws DatabaseException;
    int update(Tag tag)throws DatabaseException;
    int delete(Tag tag)throws DatabaseException;
    int getMaxId() throws  DatabaseException;
    int updateTagContact(Tag tag, Contact contact, Company company) throws DatabaseException;
    Tag getTagByName(String name)throws DatabaseException;
    boolean isExist(Tag tag) throws DatabaseException;
    List<Tag> findAll()throws DatabaseException;
    List<Tag> findAllByDealId(int id) throws DatabaseException;
    List<Tag> findAllByCompanyId(int id) throws DatabaseException;
}
