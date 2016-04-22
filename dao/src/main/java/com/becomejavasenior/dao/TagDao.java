package com.becomejavasenior.dao;


import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagDao {
    int create(Tag tag)throws DatabaseException;
    Tag getTagById(int id)throws DatabaseException;
    Tag getTagByName(String name)throws DatabaseException;
    int update(Tag tag)throws DatabaseException;
    int getMaxId() throws DatabaseException;
    int delete(Tag tag)throws DatabaseException;
    int updateTagContact(Tag tag, Contact contact, Company company) throws DatabaseException;
    List<Tag> findAll()throws DatabaseException;
    List<Tag> findAllByDealId(int id) throws DatabaseException;
    List<Tag> findAllByCompanyId(int id) throws DatabaseException;
}
