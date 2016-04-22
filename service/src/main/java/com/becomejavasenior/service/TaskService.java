package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Created by aivlev on 3/24/16.
 */
public interface TaskService {
    int create(Task task) throws DatabaseException;

    Task getTaskById(int id) throws DatabaseException;

    int update(Task task) throws DatabaseException;

    int delete(Task task) throws DatabaseException;

    List<Task> findAll() throws DatabaseException;

    Map<String, List<Task>> getTodoLineTasks(LocalDate localDate) throws DatabaseException;

    Map<LocalDate, List<Task>> getTaskByDay(LocalDate startDate, int dayCount) throws DatabaseException;

    Map<LocalTime, List<Task>> getTaskForDayByHalfHour(LocalDate day) throws DatabaseException;

    Map<LocalTime, Map<DayOfWeek, List<Task>>> getTaskByHalfHourForWeek(LocalDate day) throws DatabaseException;

    Map<LocalDate, List<Task>> getTaskForMonthByDay(LocalDate date) throws DatabaseException;

    Map<String,List<Task>> filterTasksByDone(List<Task> tasks);

    List<Task> getRunningTasks(List<Task> notDoneTasks);


    void moveOnToday(int taskId) throws DatabaseException;

    void moveOnTomorrow(int taskId) throws DatabaseException;

    void updateTaskTime(Task task, LocalDateTime newDate) throws DatabaseException;

    int getMaxId() throws  DatabaseException;

}
