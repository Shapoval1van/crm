package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.service.CompanyService;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao;

    public CompanyServiceImpl() {
        this.companyDao = new DaoFactoryImpl().getCompanyDao();
    }

    @Override
    public int create(Company company) throws DatabaseException {
        return companyDao.create(company);
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        return companyDao.getCompanyById(id);
    }

    @Override
    public int update(Company company) throws DatabaseException {
        return companyDao.update(company);
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        return companyDao.delete(company);
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        return companyDao.findAll();
    }

    @Override
    public int getCount() throws DatabaseException {
        return companyDao.getCount();
    }
}
