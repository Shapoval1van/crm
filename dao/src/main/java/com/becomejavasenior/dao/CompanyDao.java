package com.becomejavasenior.dao;


import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Task;

import java.util.List;

public interface CompanyDao {
    int create(Company company) throws DatabaseException;
    Company getCompanyById(int id) throws DatabaseException;
    int update(Company company) throws DatabaseException;
    int getMaxId()throws  DatabaseException;
    int delete(Company company) throws DatabaseException;
    List<Company> findAll() throws DatabaseException;
    int getCount() throws DatabaseException;
    Company getCompanyForTask(Task task) throws DatabaseException;
}
