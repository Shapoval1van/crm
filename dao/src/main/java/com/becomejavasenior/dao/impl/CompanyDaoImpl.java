package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);

    private static final String READ_COMPANY = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company WHERE id=?";

    private static final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COMPANY = "UPDATE company SET name=?, resposible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
            "created_by=?, date_create=?, deleted=?, date_modify=?, user_modify_id=? WHERE id=?";

    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String FIND_ALL_COMPANIES = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company";
    private static final String GET_ALL_COMPANIES_COUNT = "SELECT count(*) FROM company";

    private static final String GET_COMPANY_FOR_TASK = "SELECT company.id, company.name, company.web, company.email,company. adress, company.phone," +
            " company.phone_type_id, company.date_create, company.deleted, company.date_modify, company.user_modify_id " +
            "FROM company INNER JOIN task ON company.id = task.company_id WHERE task.id = ?";
    private static final String GET_MAX_ID = "SELECT  max(id) FROM company";

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            if( company.getResponsibleUser()!=null){
                preparedStatement.setInt(2, company.getResponsibleUser().getId());
            }else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            if(company.getPhoneType()!=null){
                preparedStatement.setInt(7, company.getPhoneType().ordinal());
            }
            else{
                preparedStatement.setNull(7,Types.INTEGER);
            }
            if(company.getCreatedByUser()!=null){
                preparedStatement.setInt(8, company.getCreatedByUser().getId());
            }
            else {
                preparedStatement.setNull(8,Types.INTEGER);
            }
            if(company.getCreationDate()!=null){
                preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getYear(),company.getCreationDate().getMonth(),
                        company.getCreationDate().getDay()));
            }
            else
            {
                preparedStatement.setNull(9,Types.DATE);
            }
            if(company.getDeleted()!=null){
                preparedStatement.setBoolean(10, company.getDeleted());
            }
            else {
                preparedStatement.setBoolean(10, false);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        Company company = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_COMPANY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyForResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        if (company == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return company;
    }

    @Override
    public int update(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            preparedStatement.setInt(7, company.getPhoneType().ordinal());
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, company.getDeleted());
            preparedStatement.setDate(11, new java.sql.Date(company.getModificationDate().getTime()));
            preparedStatement.setInt(12, company.getModifiedByUser().getId());
            preparedStatement.setInt(13, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID)){
            ResultSet resultSet  = preparedStatement.getResultSet();
            if(resultSet.next()){
                maxId = resultSet.getInt("max");
            }
        }catch (SQLException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        return maxId;
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY)) {
            preparedStatement.setInt(1, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COMPANIES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setWeb(resultSet.getString("web"));
                company.setEmail(resultSet.getString("email"));
                company.setAddress(resultSet.getString("adress"));
                company.setPhone(resultSet.getString("phone"));
                company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                company.setCreationDate(resultSet.getDate("date_create"));
                company.setDeleted(resultSet.getBoolean("deleted"));
                company.setModificationDate(resultSet.getDate("date_modify"));
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting companies was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return companies;
    }

    @Override
    public int getCount() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COMPANIES_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting companies was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public Company getCompanyForTask(Task task) throws DatabaseException {
        Company company;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY_FOR_TASK)) {
            preparedStatement.setInt(1, task.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyForResultSet(resultSet);
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return company;
    }

    private Company getCompanyForResultSet(ResultSet resultSet) throws SQLException {
        Company company;
        company = new Company();
        company.setId(resultSet.getInt("id"));
        company.setName(resultSet.getString("name"));
        company.setWeb(resultSet.getString("web"));
        company.setEmail(resultSet.getString("email"));
        company.setAddress(resultSet.getString("adress"));
        company.setPhone(resultSet.getString("phone"));
        company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
        company.setCreationDate(resultSet.getDate("date_create"));
        company.setDeleted(resultSet.getBoolean("deleted"));
        company.setModificationDate(resultSet.getDate("date_modify"));
        return company;
    }
}

    /*id serial NOT NULL,
    name character varying(255) NOT NULL,
    responsible_id integer,
    web character varying(255),
    email character varying(255) NOT NULL,
    adress character varying(255),
    phone character varying(45) NOT NULL,
    phone_type_id integer NOT NULL,
    created_by integer NOT NULL,
    date_create timestamp without time zone NOT NULL,
    deleted boolean NOT NULL DEFAULT false,*/