package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CurrenciesDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Currencies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDaoImpl  extends CommonDao implements CurrenciesDao {

    private static final Logger LOGGER = LogManager.getLogger(CurrenciesDaoImpl.class);

    private String  CREATE_CURRENCIES = "INSERT INTO currencies (name, active_currency) VALUES (?, ?)";
    private String  READ_ACTIVE_CURRENCIES="SELECT id, name, active_currency FROM currencies WHERE active_currency = true";
    private String  READ_ALL_CURRENCIES = "SELECT id, name, active_currency FROM currencies";

    public CurrenciesDaoImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public int create(Currencies currencies) throws DatabaseException {
        return 0;
    }

    @Override
    public Currencies read(int id) throws DatabaseException {
        return null;
    }

    @Override
    public int update(Currencies currencies) throws DatabaseException {
        return 0;
    }

    @Override
    public int delete(Currencies currencies) throws DatabaseException {
        return 0;
    }

    @Override
    public List<Currencies> findAll() throws DatabaseException {
        List<Currencies> currenciesList = new ArrayList<Currencies>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_CURRENCIES);
        ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()){
                Currencies currencies = new Currencies();
                currencies.setId(resultSet.getInt("id"));
                currencies.setName(resultSet.getString("name"));
                currencies.setActiveCurrency(resultSet.getBoolean(3));
                currenciesList.add(currencies);
            }
        } catch (SQLException e) {
            LOGGER.error("Creating a currencies was failed. Error - {}", new Object[]{e.getMessage()});
        }
        return currenciesList;
    }

    @Override
    public Currencies getActiveCurrencies() throws DatabaseException {
        Currencies currencies = new Currencies();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ACTIVE_CURRENCIES);
             ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    currencies.setId(resultSet.getInt("id"));
                    currencies.setName(resultSet.getString("name"));
                    currencies.setActiveCurrency(resultSet.getBoolean(3));
                }
        } catch (SQLException e) {
            LOGGER.error("Creating a currencies was failed. Error - {}", new Object[]{e.getMessage()});
        }
        return currencies;
    }
}
