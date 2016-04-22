package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskTypeDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.TaskType;
import com.becomejavasenior.service.TaskTypeService;

import java.util.List;


public class TaskTypeServiceImpl implements TaskTypeService {
    private TaskTypeDao taskTypeDao;

    public TaskTypeServiceImpl() {
        this.taskTypeDao = new DaoFactoryImpl().getTaskTypeDao();
    }
    @Override
    public int create(TaskType taskType) throws DatabaseException {
        return taskTypeDao.create(taskType);
    }

    @Override
    public TaskType getTaskTypeById(int id) throws DatabaseException {
        return taskTypeDao.getTaskTypeById(id);
    }

    @Override
    public List<TaskType> findAll() throws DatabaseException {
        return taskTypeDao.findAll();
    }
}
