package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public interface CompanyService {

    int create(Company company) throws DatabaseException;

    Company getCompanyById(int id) throws DatabaseException;

    int update(Company company) throws DatabaseException;

    int delete(Company company) throws DatabaseException;

    List<Company> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;

}
