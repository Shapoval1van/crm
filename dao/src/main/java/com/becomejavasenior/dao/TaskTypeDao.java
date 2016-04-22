package com.becomejavasenior.dao;

import com.becomejavasenior.model.TaskType;

import java.util.List;

public interface TaskTypeDao {
    int create(TaskType taskType) throws DatabaseException;
    TaskType getTaskTypeById(int id) throws DatabaseException;
    List<TaskType> findAll() throws DatabaseException;
}
