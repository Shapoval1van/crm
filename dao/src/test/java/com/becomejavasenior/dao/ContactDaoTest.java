package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class ContactDaoTest extends AbstractTestDao {
    public static final String CONTACT_TEST_DATA_XML = "contactTestData.xml";
    public static final int NO_CONTACT_FOR_TASK1 = 1;
    public static final int CONTACT4_FOR_TASK4 = 4;
    private ContactDao contactDao = new ContactDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());

    @Test
    public void testGetContactForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_CONTACT_FOR_TASK1);
        Contact contact = contactDao.getContactForTask(task1);
        assertThat(contact, nullValue());
    }

    @Test
    public void testGetContactForTaskReturnContact() throws DatabaseException {
        Task task4 = taskDao.getTaskById(CONTACT4_FOR_TASK4);
        Contact contact = contactDao.getContactForTask(task4);
        assertThat(contact, Matchers.notNullValue());
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(CONTACT_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
