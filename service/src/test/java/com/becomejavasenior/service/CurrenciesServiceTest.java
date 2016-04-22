package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.CurrenciesDaoImpl;
import com.becomejavasenior.model.Currencies;
import com.becomejavasenior.service.impl.CurrenciesServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CurrenciesServiceImpl.class)
public class CurrenciesServiceTest {

    private CurrenciesService currenciesService;

    @Mock
    private CurrenciesDaoImpl currenciesDao;

    @Mock
    private Currencies currencies;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getCurrenciesDao()).thenReturn(currenciesDao);
        currenciesService = new CurrenciesServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        when(currenciesDao.create(any(Currencies.class))).thenReturn(1);
        currenciesService.create(currencies);

        verify(currenciesDao).create(currencies);
    }

    @Test
    public void testGetCurrenciesById() throws Exception {
        PowerMockito.when(currenciesDao.read(1)).thenReturn(currencies);

        Currencies resultCurrencies = currenciesService.read(1);

        verify(currenciesDao).read(1);
        Assert.assertEquals(currencies, resultCurrencies);

    }

    @Test
    public void testUpdate() throws Exception {
        when(currenciesDao.update(any(Currencies.class))).thenReturn(1);
        currenciesService.update(currencies);

        verify(currenciesDao).update(currencies);
    }

    @Test
    public void testDelete() throws Exception {
        when(currenciesDao.delete(any(Currencies.class))).thenReturn(1);
        currenciesService.delete(currencies);

        verify(currenciesDao).delete(currencies);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Currencies> currenciess = new LinkedList<>();

        Currencies firstCurrencies  = new Currencies();
        firstCurrencies.setId(1);
        currenciess.add(firstCurrencies);

        Currencies secondCurrencies = new  Currencies();
        secondCurrencies.setId(2);
        currenciess.add(secondCurrencies);

        when(currenciesDao.findAll()).thenReturn(currenciess);
        List<Currencies> result = currenciesService.findAll();

        verify(currenciesDao).findAll();
        assertEquals(2, result.size());
    }


}
