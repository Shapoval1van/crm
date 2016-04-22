package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.NoteDaoImpl;
import com.becomejavasenior.model.Note;
import com.becomejavasenior.service.NoteService;

import java.util.List;


public class NoteServiceImpl implements NoteService {
    NoteDaoImpl noteDao;
    public NoteServiceImpl(){
        noteDao = (NoteDaoImpl) new DaoFactoryImpl().getNoteDao();
    }
    @Override
    public int create(Note note) throws DatabaseException {
        return noteDao.create(note);
    }

    @Override
    public Note getNoteById(int id) throws DatabaseException {
        return noteDao.getNoteById(id);
    }

    @Override
    public int update(Note note) throws DatabaseException {
        return noteDao.update(note);
    }

    @Override
    public int delete(Note note) throws DatabaseException {
        return noteDao.delete(note);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return noteDao.getMaxId();
    }

    @Override
    public List<Note> findAll() throws DatabaseException {
        return noteDao.findAll();
    }

    @Override
    public List<Note> findAllByDealId(int id) throws DatabaseException {
        return noteDao.findAllByDealId(id);
    }

    @Override
    public List<Note> findAllByCompanyId(int id) throws DatabaseException {
        return findAllByCompanyId(id);
    }
}
