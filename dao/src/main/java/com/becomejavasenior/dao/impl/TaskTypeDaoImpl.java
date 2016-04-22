package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskTypeDao;
import com.becomejavasenior.model.TaskType;
import org.apache.logging.log4j.LogManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskTypeDaoImpl extends CommonDao implements TaskTypeDao {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TaskDaoImpl.class);

    private static String GET_TASK_TYPE_BY_ID= "SELECT id, type_name FROM task_type WHERE id = ?";
    private static String INSERT_TASK_TYPE = "INSERT INTO task_type(id, type_name) VALUES (DEFAULT, ?)";
    private static String FINDL_ALL = "SELECT id, type_name FROM task_type";

    public TaskTypeDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(TaskType taskType) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_TASK_TYPE)) {
            ps.setString(1, taskType.getTypeName());

            return ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Creating a task type was failed. Error - {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public TaskType getTaskTypeById(int id) throws DatabaseException {
        TaskType taskType = new TaskType();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TASK_TYPE_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            taskType.setTypeName(resultSet.getString("type_name"));
            taskType.setId(resultSet.getInt("id"));

        } catch (SQLException e) {
            LOGGER.error("Getting a task was failed. Error - {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
        return taskType;
    }

    @Override
    public List<TaskType> findAll() throws DatabaseException {
        List<TaskType> taskTypes = new ArrayList<TaskType>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FINDL_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                TaskType taskType = new TaskType();
                taskType.setTypeName(resultSet.getString("type_name"));
                taskType.setId(resultSet.getInt("id"));
                taskTypes.add(taskType);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a task was failed. Error - {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
        return taskTypes;
    }
}
