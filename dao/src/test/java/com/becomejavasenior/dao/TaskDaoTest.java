package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.dao.impl.UserDaoImpl;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class TaskDaoTest extends AbstractTestDao {

    public static final int TEST_TASK_ID = 1;
    public static final int TEST_CONTACT_ID = 1;
    public static final int TEST_COMPANY_ID = 1;
    public static final int TEST_DEAL_ID = 1;
    public static final String TASK_TEST_DATA_XML = "taskTestData.xml";
    TaskDao taskDao = new TaskDaoImpl(getDataSource());
    UserDao userDao = new UserDaoImpl(getDataSource());

    @Test
    public void testReadTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        assertThat(task, is(notNullValue()));
    }

    @Test
    public void testAddTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        task.setResponsibleUser(userDao.getResponsibleUserForTask(task));
        task.setCreatedByUser(userDao.createdByUserForTask(task));
        int newTaskId = taskDao.create(task);
        Task newTask = taskDao.getTaskById(newTaskId);
        assertThat(task.getName(), equalTo(newTask.getName()));
    }


    @Test(expected = DatabaseException.class)
    public void testDeleteTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        taskDao.delete(task);
        Task deletedTask = taskDao.getTaskById(1);
    }


    @Test
    public void testUpdatedTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        task.setResponsibleUser(userDao.getResponsibleUserForTask(task));
        task.setCreatedByUser(userDao.createdByUserForTask(task));
        String newName = "new name";
        task.setName(newName);
        taskDao.update(task);

        Task updatedTask = taskDao.getTaskById(1);

        assertThat(updatedTask.getName(), equalTo(newName));
    }

    @Test
    public void testGetAllTasksForContactById() throws DatabaseException {
        List<Task> allTasksForContactBy1 = taskDao.getAllTasksForContactById(TEST_CONTACT_ID);
        int taskCountForCompanyId1 = 2;
        assertThat(allTasksForContactBy1, hasSize(taskCountForCompanyId1));
    }

    @Test
    public void testGetAllTasksForCompanyById() throws DatabaseException {
        List<Task> allTasksForCompanyBy1 = taskDao.getAllTasksForCompanyById(TEST_COMPANY_ID);
        int taskCountForCompanyId1 = 4;
        assertThat(allTasksForCompanyBy1, hasSize(taskCountForCompanyId1));
    }

    @Test
    public void testGetAllTasksForDealById() throws DatabaseException {
        List<Task> allTasksForDealBy1 = taskDao.getAllTasksForDealById(TEST_DEAL_ID);
        int taskCountForDealId1 = 25;
        assertThat(allTasksForDealBy1, hasSize(taskCountForDealId1));
    }

    @Test
    public void testGetAllTasks() throws DatabaseException {
        List<Task> allTasksForDealBy1 = taskDao.findAll();
        int allTaskCount = 31;
        assertThat(allTasksForDealBy1, hasSize(allTaskCount));
    }
    
    @Test
    public void testGetTasksBetweenDays() throws DatabaseException {
        LocalDate startDay = LocalDate.of(2016, 4, 12);
        LocalDate finishDay = LocalDate.of(2016, 4, 15);
        List<Task> tasksBetweenDays = taskDao.getTasksBetweenDays(startDay, finishDay);
        int tasksBetweenCurrentDays = 4;
        assertThat(tasksBetweenDays, hasSize(tasksBetweenCurrentDays));
    }

    @Test
    public void testGetTasksBetweenDaysNoTasks() throws DatabaseException {
        LocalDate startDay = LocalDate.of(2017, 3, 10);
        LocalDate finishDay = LocalDate.of(2017, 3, 10);
        List<Task> tasksBetweenDays = taskDao.getTasksBetweenDays(startDay, finishDay);
        int tasksBetweenCurrentDays = 0;
        assertThat(tasksBetweenDays, hasSize(tasksBetweenCurrentDays));
    }


    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(TASK_TEST_DATA_XML);
        return new FlatXmlDataSetBuilder().build(resourceAsStream);
    }
}
