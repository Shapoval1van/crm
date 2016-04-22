package com.becomejavasenior.dao;

import com.becomejavasenior.model.Currencies;

import java.util.List;

public interface CurrenciesDao {
    int  create(Currencies currencies) throws DatabaseException;
    Currencies read(int id) throws DatabaseException;
    int update(Currencies currencies) throws DatabaseException;
    int delete(Currencies currencies) throws DatabaseException;
    List<Currencies> findAll() throws DatabaseException;
    Currencies getActiveCurrencies() throws DatabaseException;
}
