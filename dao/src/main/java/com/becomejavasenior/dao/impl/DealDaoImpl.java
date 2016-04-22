package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.*;
import com.becomejavasenior.model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationException;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {

    private static final Logger LOGGER = LogManager.getLogger(DealDaoImpl.class);

    private DaoFactoryImpl daoFactory;

    private static final String READ_DEAL = "SELECT id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted FROM deal WHERE id=?";

    private static final String CREATE_DEAL = "INSERT INTO deal (id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL = "INSERT INTO deal_contact(deal_id, contact_id) " +
            "VALUES(?, ?)";

    private static final String UPDATE_DEAL = "UPDATE deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
            "company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";


    private static final String DELETE_DEAL = "DELETE FROM deal WHERE id=?";
    private static final String GET_MAX_ID = "SELECT MAX(id) FROM deal";
    private static final String FIND_ALL_DEALS = "SELECT id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted FROM deal";
    private static final String COUNT_DEALS_WITH_TASKS = "SELECT count(*) FROM deal d WHERE d.id IN (SELECT t.id FROM task t)";
    private static final String COUNT_DEALS_WITHOUT_TASKS = "SELECT count(*) FROM deal d WHERE d.id NOT IN (SELECT t.id FROM task t)";

    private final String FIND_ALL_DEAL_FOR_CONTACT = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            "FROM deal JOIN deal_contact ON deal.id = deal_contact.deal_id AND contact_id = ? AND deleted = FALSE ";

    private final String FIND_DEAL_FOR_COMPANY = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            " FROM deal WHERE company_id=? AND deleted = FALSE ";

    private static final String GET_DEAL_FOR_TASK = "SELECT deal.id, deal.name, deal.budget, deal.responsible_id, deal.stage_id, deal.company_id, deal.created_by, deal.date_create, deal.deleted " +
            "FROM deal INNER JOIN task ON deal.id = task.deal_id WHERE task.id = ?";

    private static final String CREATE_DEAL_FOR_CONTACT = "INSERT INTO deal_contact (contact_id, deal_id) VALUES (?, ?)";

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
        daoFactory = new DaoFactoryImpl();
    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEAL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.setString(2, deal.getName());
            preparedStatement.setBigDecimal(3, deal.getBudget());
            if(deal.getResponsibleUser()!=null){
                preparedStatement.setInt(4, deal.getResponsibleUser().getId());
            }
            else{
                preparedStatement.setNull(4, Types.INTEGER);
            }
            if(deal.getDealStage()!=null) {
                preparedStatement.setInt(5, deal.getDealStage().ordinal());
            }
            else{
                preparedStatement.setNull(5,Types.INTEGER);
            }
            if (deal.getCompany()!=null){
                preparedStatement.setInt(6, deal.getCompany().getId());
            }
            else{
                preparedStatement.setNull(6,Types.INTEGER);
            }
            if(deal.getCreatedByUser()!=null){
                preparedStatement.setInt(7, deal.getCreatedByUser().getId());
            }
            else {
                preparedStatement.setNull(7,Types.INTEGER);
            }
            if(deal.getCreationDate()!=null){
                preparedStatement.setDate(8, new java.sql.Date(deal.getCreationDate().getTime()));
            }
            else {
                preparedStatement.setNull(8,Types.DATE);
            }
            if (deal.getDeleted()!=null){
                preparedStatement.setBoolean(9, deal.getDeleted());
            }
            else {
                preparedStatement.setBoolean(9,false);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        Deal deal;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_DEAL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            deal = getDealByResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Getting a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        if (deal == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return deal;
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEAL)) {
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
            preparedStatement.setInt(4, deal.getDealStage().ordinal());
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setBoolean(8, deal.getDeleted());
            preparedStatement.setInt(9, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int updateDealContact(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL)) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.setInt(2, deal.getContacts().get(0).getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try(Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                maxId = resultSet.getInt("max");
            }
        } catch (SQLException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        return maxId;
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEAL)) {
            preparedStatement.setInt(1, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        List<Deal> deals = new ArrayList<Deal>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEALS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Deal deal = getDealById(resultSet.getInt("id"));
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DEALS_WITH_TASKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting deals with tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DEALS_WITHOUT_TASKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting deals without tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public List<Deal> getDealsForContactById(Contact contact) throws DatabaseException {
        List<Deal> deals = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEAL_FOR_CONTACT)) {
            preparedStatement.setInt(1, contact.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Deal deal = getDealById(resultSet.getInt("id"));
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals for contact was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public List<Deal> getDealsForCompanyById(Company company) throws DatabaseException {
        List<Deal> deals = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEAL_FOR_COMPANY)) {
            preparedStatement.setInt(1, company.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Deal deal = getDealById(resultSet.getInt(1));
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals for company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public Deal getDealForTask(Task task) throws DatabaseException {
        Deal deal;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEAL_FOR_TASK)) {
            preparedStatement.setInt(1, task.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getDealByResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    @Override
    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        if (deal.getId() == 0){
            int dealId = this.create(deal);
            deal.setId(dealId);
        }

        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEAL_FOR_CONTACT);
            preparedStatement.setInt(1, contactId);
            preparedStatement.setInt(2, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal_contact was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    private Deal getDealByResultSet(ResultSet resultSet) throws DatabaseException, SQLException {
        List<Note> notes;
        List<File> files;
        List<Tag> tags;
        List<Contact> contacts;

        Deal deal = new Deal();
        deal.setId(resultSet.getInt("id"));
        deal.setName(resultSet.getString("name"));
        deal.setBudget(resultSet.getBigDecimal("budget"));
        if (resultSet.getInt("responsible_id")!=0) {
            deal.setResponsibleUser(daoFactory.getUserDao().getUserById(resultSet.getInt("responsible_id")));
        }
        deal.setDealStage(DealStage.getDealStageById(resultSet.getInt("stage_id")));
        if(resultSet.getInt("company_id")!=0){
            deal.setCompany(daoFactory.getCompanyDao().getCompanyById(resultSet.getInt("company_id")));
        }
        if(resultSet.getInt("created_by")!=0){
            deal.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
        }
        deal.setCreationDate(resultSet.getDate("date_create"));
        deal.setDeleted(resultSet.getBoolean("deleted"));

        notes = daoFactory.getNoteDao().findAllByDealId(deal.getId());

        files = daoFactory.getFileDao().findAllByDealId(deal.getId());

        tags = daoFactory.getTagDao().findAllByDealId(deal.getId());

        contacts = daoFactory.getContactDao().findAllByDealId(deal.getId());

        deal.setNotes(notes);
        deal.setFiles(files);
        deal.setTags(tags);
        deal.setContacts(contacts);
        return deal;
    }

}