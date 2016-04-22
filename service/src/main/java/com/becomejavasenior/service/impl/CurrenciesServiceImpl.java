package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CurrenciesDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Currencies;
import com.becomejavasenior.service.CurrenciesService;

import java.util.List;

/**
 * Created by vanya on 05.04.2016.
 */
public class CurrenciesServiceImpl implements CurrenciesService {
    CurrenciesDao currenciesDao = null;
    public CurrenciesServiceImpl() { this.currenciesDao =  new  DaoFactoryImpl().getCurrenciesDao();
    }

    @Override
    public int create(Currencies currencies) throws DatabaseException {
        return currenciesDao.create(currencies);
    }

    @Override
    public Currencies read(int id) throws DatabaseException {
        return currenciesDao.read(id);
    }

    @Override
    public int update(Currencies currencies) throws DatabaseException {
        return currenciesDao.update(currencies);
    }

    @Override
    public int delete(Currencies currencies) throws DatabaseException {
        return currenciesDao.delete(currencies);
    }

    @Override
    public List<Currencies> findAll() throws DatabaseException {
        return currenciesDao.findAll();
    }

    @Override
    public Currencies getActiveCurrencies() throws DatabaseException {
        return currenciesDao.getActiveCurrencies();
    }
}
