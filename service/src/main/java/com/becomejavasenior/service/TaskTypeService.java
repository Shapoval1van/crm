package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.TaskType;

import java.util.List;

public interface  TaskTypeService {
    int create(TaskType taskType) throws DatabaseException;
    TaskType getTaskTypeById(int id) throws DatabaseException;
    List<TaskType> findAll() throws DatabaseException;
}
