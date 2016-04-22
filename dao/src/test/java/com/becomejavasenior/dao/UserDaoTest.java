package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.dao.impl.UserDaoImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;
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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class UserDaoTest extends AbstractTestDao {
    public static final String TEST_DATA_XML = "userTestData.xml";
    public static final int TASK4 = 4;
    public static final int CONTACT4_FOR_TASK4 = 4;
    private UserDao userDao = new UserDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());

    @Test
    public void testGetResponsibleUserForTask() throws DatabaseException {
        Task task4 = taskDao.getTaskById(TASK4);
        User responsibleUser = userDao.getResponsibleUserForTask(task4);
        assertThat(responsibleUser, notNullValue());
        String expectedResponsibleUserName = "Andrey Yarmolenko";
        assertThat(responsibleUser.getName(), equalTo(expectedResponsibleUserName));
    }

    @Test
    public void testGetCreatedByUserForTask() throws DatabaseException {
        Task task4 = taskDao.getTaskById(TASK4);
        User createdByUser = userDao.createdByUserForTask(task4);
        assertThat(createdByUser, notNullValue());
        String expectedCreatedByUserName = "Yevgeny Khacheridi";
        assertThat(createdByUser.getName(), equalTo(expectedCreatedByUserName));
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
