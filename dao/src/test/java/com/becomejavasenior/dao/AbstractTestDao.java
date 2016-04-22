package com.becomejavasenior.dao;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.After;
import org.junit.Before;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractTestDao {
    private static final String DEFAULT_DATA_XML_PATH = "defaultData.xml";
    private static final String TESTDB_CONFIG_PROPERTIES = "testdb_config.properties";
    private IDatabaseTester databaseTester;
    private  Properties props = getProperties();


    @Before
    public void init() throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(getDataSource().getConnection(),  props.getProperty("currentSchema"));
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        databaseTester = new DefaultDatabaseTester(connection);

        IDataSet dataSet = getDataSet();
        databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
        fixIndexes();
    }

    @After
    public void clean() throws Exception {
      //  databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
        databaseTester.onTearDown();
    }


    protected DataSource getDataSource() {
        String connectUri = props.getProperty("url") + "?currentSchema=" + props.getProperty("currentSchema");
        ConnectionFactory connectionFactory =  new DriverManagerConnectionFactory(connectUri, props);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =  new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        return new PoolingDataSource<>(connectionPool);
    }

    private Properties getProperties(){
        Properties props = new Properties();
        try(InputStream fis = getClass().getClassLoader().getResourceAsStream(TESTDB_CONFIG_PROPERTIES)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("can't find properties");
        }
        return props;
    }

    protected IDataSet getDataSet() throws Exception {
        InputStream resourceAsStream =  getClass().getClassLoader().getResourceAsStream(DEFAULT_DATA_XML_PATH);
        ReplacementDataSet defaultDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        defaultDataSet.addReplacementObject("[null]", null);

        IDataSet[] dataSets = {defaultDataSet, getSpecificDataSet()};
        return new CompositeDataSet(dataSets);
    }

    private void fixIndexes() throws SQLException {
        Connection connection = getDataSource().getConnection();
        String query = "SELECT setval('company_id_seq', (SELECT MAX(id) FROM company));\n" +
                "SELECT setval('contact_id_seq', (SELECT MAX(id) from contact));\n" +
                "SELECT setval('deal_id_seq', (SELECT MAX(id) from deal));\n" +
                "SELECT setval('file_id_seq', (SELECT MAX(id) from file));\n" +
                "SELECT setval('note_id_seq', (SELECT MAX(id) from note));\n" +
                "SELECT setval('person_id_seq', (SELECT MAX(id) from person));\n" +
                "SELECT setval('tag_id_seq', (SELECT MAX(id) from tag));\n" +
                "SELECT setval('task_type_id_seq', (SELECT MAX(id) from task_type));\n" +
                "SELECT setval('task_id_seq', (SELECT MAX(id) from task));\n";
        connection.prepareStatement(query).execute();
    }

    abstract protected IDataSet getSpecificDataSet() throws DataSetException;
}
