package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.TagDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    TagDaoImpl  tagDao;

    public TagServiceImpl() {
        this.tagDao = (TagDaoImpl) new DaoFactoryImpl().getTagDao();
    }

    @Override
    public int create(Tag tag) throws DatabaseException {
        return tagDao.create(tag);
    }

    @Override
    public Tag getTagById(int id) throws DatabaseException {
        return tagDao.getTagById(id);
    }

    @Override
    public int update(Tag tag) throws DatabaseException {
        return tagDao.update(tag);
    }

    @Override
    public int delete(Tag tag) throws DatabaseException {
        return tagDao.delete(tag);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return tagDao.getMaxId();
    }

    @Override
    public int updateTagContact(Tag tag, Contact contact, Company company) throws DatabaseException {
        return tagDao.updateTagContact(tag, contact, company);
    }

    @Override
    public Tag getTagByName(String name) throws DatabaseException {
        return tagDao.getTagByName(name);
    }

    @Override
    public boolean isExist(Tag tag) throws DatabaseException {
        List<Tag> tags = tagDao.findAll();
        for(Tag iterator: tags){
            if(iterator.getName().equals(tag.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tag> findAll() throws DatabaseException {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findAllByDealId(int id) throws DatabaseException {
        return tagDao.findAllByDealId(id);
    }

    @Override
    public List<Tag> findAllByCompanyId(int id) throws DatabaseException {
        return tagDao.findAllByDealId(id);
    }
}
