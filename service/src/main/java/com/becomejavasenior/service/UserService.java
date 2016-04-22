package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;

import com.becomejavasenior.model.User;

import java.util.List;

/**
 * Created by vanya on 06.04.2016.
 */
public interface UserService {
    // TODO test
    User getUserByName(String  name)throws DatabaseException;
    int create(User user) throws DatabaseException;
    User getUserById(int id) throws DatabaseException;
    int update(User user) throws DatabaseException;
    int delete(User user) throws DatabaseException;
    List<User> findAll() throws DatabaseException;
}

