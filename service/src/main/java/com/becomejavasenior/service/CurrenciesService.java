package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Currencies;

import java.util.List;

/**
 * Created by vanya on 05.04.2016.
 */
public interface CurrenciesService {
    int  create(Currencies currencies) throws DatabaseException;
    Currencies read(int id) throws DatabaseException;
    int update(Currencies currencies) throws DatabaseException;
    int delete(Currencies currencies) throws DatabaseException;
    List<Currencies> findAll() throws DatabaseException;
    Currencies getActiveCurrencies() throws DatabaseException;
}
