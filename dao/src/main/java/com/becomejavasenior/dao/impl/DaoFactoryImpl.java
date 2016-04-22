package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.*;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory {

    private static final Logger LOGGER = LogManager.getLogger(DaoFactoryImpl.class);

    private static DataSource dataSource;

    public DaoFactoryImpl() {
    }

    private DataSource initDataSource(){
        Properties props = getProperties();
        try {
            Class.forName(props.getProperty("driverClassName"));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Getting a driver was failed. Error - {}", new Object[]{e.getMessage()});
        }
        ConnectionFactory connectionFactory =  new DriverManagerConnectionFactory(props.getProperty("url"), props);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =  new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        return dataSource;
    }

    private Properties getProperties(){
        Properties props = new Properties();
        try(InputStream fis = getClass().getClassLoader().getResourceAsStream("db_config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            LOGGER.error("Getting a driver was failed. Error - {}", new Object[]{e.getMessage()});
        }
        return props;
    }

    public CompanyDao getCompanyDao() {
        return new CompanyDaoImpl(getDataSource());
    }

    public ContactDao getContactDao() {
        return new ContactDaoImpl(getDataSource());
    }

    public DealDao getDealDao() {
        return new DealDaoImpl(getDataSource());
    }

    public FileDao getFileDao() {
        return new FileDaoImpl(getDataSource());
    }

    public LanguageDao getLanguageDao() {
        return new LanguageDaoImpl(getDataSource());
    }

    public NoteDao getNoteDao() {
        return new NoteDaoImpl(getDataSource());
    }

    public RoleDao getRoleDao() {
        return new RoleDaoImpl(getDataSource());
    }

    public TagDao getTagDao() {
        return new TagDaoImpl(getDataSource());
    }

    public TaskDao getTaskDao() {
        return new TaskDaoImpl(getDataSource());
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(getDataSource());
    }

    public TaskTypeDao getTaskTypeDao() {
        return new TaskTypeDaoImpl(getDataSource());
    }

    public CurrenciesDao getCurrenciesDao(){return  new CurrenciesDaoImpl(getDataSource());}

    private DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = initDataSource();
        }
        return dataSource;
    }
}
