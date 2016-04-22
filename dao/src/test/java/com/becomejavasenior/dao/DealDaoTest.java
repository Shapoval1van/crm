package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.CompanyDaoImpl;
import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class DealDaoTest extends AbstractTestDao {
    public static final String DEAL_TEST_DATA_XML = "dealTestData.xml";
    public static final int NO_DEAL_FOR_TASK2 = 2;
    public static final int DEAL4_FOR_TASK4 = 4;
    public static final int DEAL1_ID = 1;
    public static final int COMPANY1_ID = 1;
    public static final int COUNT_DEALS_FOR_COMPANY1 = 3;
    public static final int CONTACT1_ID = 1;
    public static final int COUNT_DEALS_FOR_CONTACT1 = 3;
    public static final int DEAL_COUNT = 5;
    private DealDao dealDao = new DealDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());
    private CompanyDao companyDao = new CompanyDaoImpl(getDataSource());
    private ContactDao contactDao = new ContactDaoImpl(getDataSource());

    @Test
    public void testReadDeal() throws DatabaseException {
        Deal deal = dealDao.getDealById(DEAL1_ID);
        assertThat(deal, is(notNullValue()));
    }

    @Test
    public void testAddDeal() throws DatabaseException {
        Deal deal = dealDao.getDealById(DEAL1_ID);
        int newDealId = deal.getId();
        Deal newDeal = dealDao.getDealById(newDealId);
        assertThat(newDeal.getName(), equalTo(deal.getName()));
    }

    @Test(expected = DatabaseException.class)
    public void testDeleteDeal() throws DatabaseException {
        Deal deal = dealDao.getDealById(DEAL1_ID);
        dealDao.delete(deal);
        Deal deletedDeal = dealDao.getDealById(DEAL1_ID);
    }

    @Test
    public void testUpdateDeal() throws DatabaseException {
        Deal deal = dealDao.getDealById(DEAL1_ID);
        String newName = "NewName";
        deal.setName(newName);
        dealDao.update(deal);

        Deal updatedDeal = dealDao.getDealById(DEAL1_ID);

        assertThat(newName, equalTo(updatedDeal.getName()));
    }

    @Test
    public void testReadAllDeals() throws DatabaseException {
        List<Deal> deals = dealDao.findAll();
        assertThat(deals.size(), equalTo(DEAL_COUNT));
    }

    @Test
    public void testGetDealsByCompanyId() throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY1_ID);
        List<Deal> deals = dealDao.getDealsForCompanyById(company);
        assertThat(deals.size(), equalTo(COUNT_DEALS_FOR_COMPANY1));
    }

    @Test
    public void testGetDealsByContactId() throws DatabaseException {
        Contact contact = contactDao.getContactById(CONTACT1_ID);
        List<Deal> deals = dealDao.getDealsForContactById(contact);
        assertThat(deals.size(), equalTo(COUNT_DEALS_FOR_CONTACT1));
    }

    @Test
    public void testGetDealForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_DEAL_FOR_TASK2);
        Deal deal = dealDao.getDealForTask(task1);
        assertThat(deal, nullValue());
    }

    @Test
    public void testGetDealForTaskReturnDeal() throws DatabaseException {
        Task task4 = taskDao.getTaskById(DEAL4_FOR_TASK4);
        Deal deal = dealDao.getDealForTask(task4);
        assertThat(deal, Matchers.notNullValue());
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(DEAL_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
