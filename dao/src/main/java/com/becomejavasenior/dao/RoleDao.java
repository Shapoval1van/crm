package com.becomejavasenior.dao;

import com.becomejavasenior.model.Role;

import java.util.List;

public interface RoleDao {
    int create(Role role) throws DatabaseException;
    Role getRoleById(int id) throws DatabaseException;
    int update(Role role) throws DatabaseException;
    int delete(Role role) throws DatabaseException;
    List<Role> findAll() throws DatabaseException;
}
