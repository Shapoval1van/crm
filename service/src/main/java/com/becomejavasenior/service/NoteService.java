package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Note;

import java.util.List;


public interface NoteService {
    int create(Note note) throws DatabaseException;
    Note getNoteById(int id) throws DatabaseException;
    int update(Note note) throws DatabaseException;
    int delete(Note note) throws DatabaseException;
    int getMaxId()throws DatabaseException;
    List<Note> findAll() throws DatabaseException;
    List<Note> findAllByDealId(int id) throws DatabaseException;
    List<Note> findAllByCompanyId(int id) throws DatabaseException;
}
