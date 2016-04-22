package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {

    private static final Logger LOGGER = LogManager.getLogger(TagDaoImpl.class);

    private static final String FIND_ALL_BY_DEAL_ID = "SELECT * FROM tag JOIN tag_deal ON" +
            " tag.id = tag_deal.tag_id AND deal_id = ?";
    private static final String FIND_BY_NAME = "SELECT  id, name, created_by, date_create FROM tag WHERE name = ?";
    //private static final String
    private static final String CREATE = "INSERT INTO tag(id, name, created_by, date_create )VALUES (DEFAULT,?,?,?)";
    private static final String CREATE_RELATIVE_WITH_TAG_AD_CONTACT =
            "INSERT INTO tag_contact_company(tag_id, contact_id, company_id) VALUES (?,?,?)";
    private static final String FIND_ALL = "SELECT  id, name, created_by, date_create FROM tag";
    private static final String GET_MAX_ID = "SELECT max(id) FROM tag";
    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Tag tag) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, tag.getName());
            //TODO
            preparedStatement.setInt(2,2);
            preparedStatement.setDate(3, new java.sql.Date(tag.getCreationDate().getYear(),
                    tag.getCreationDate().getMonth(),tag.getCreationDate().getDay()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Tag getTagById(int id) {
        return null;
    }

    @Override
    public Tag getTagByName(String name) throws DatabaseException {
        Tag tag = new Tag();
       try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
           preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tag.setId(resultSet.getInt("id"));
                tag.setName(resultSet.getString("name"));
                tag.setCreationDate(resultSet.getDate(4));
                tag.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt(3)));
            }
           return  tag;
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int update(Tag tag) {
        return 0;
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID)){
            ResultSet resultSet  = preparedStatement.executeQuery();
            if(resultSet.next()){
                maxId = resultSet.getInt("max");
            }
        }catch (SQLException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        return maxId;
    }

    @Override
    public int delete(Tag tag) {
        return 0;
    }

    @Override
    public int updateTagContact(Tag tag, Contact contact, Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RELATIVE_WITH_TAG_AD_CONTACT)) {
            preparedStatement.setInt(1, tag.getId());
            preparedStatement.setInt(2,contact.getId());
            preparedStatement.setInt(3, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Tag> findAll()throws DatabaseException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setName(resultSet.getString("name"));
                tag.setCreationDate(resultSet.getDate(4));
                tag.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt(3)));
                tags.add(tag);
            }
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> findAllByDealId(int id) throws DatabaseException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt(1));
                tag.setName(resultSet.getString(2));
                tag.setCreationDate(resultSet.getDate(4));
                tag.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> findAllByCompanyId(int id) throws DatabaseException {
        return null;
    }


}
