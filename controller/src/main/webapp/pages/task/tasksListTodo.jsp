<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Задачи">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary active" role="button">Список</a>
            <a href="?view=todo" class="btn btn-primary disabled" role="button">To-do line</a>
            <a href="?view=day" class="btn btn-primary active" role="button">День</a>
            <a href="?view=week" class="btn btn-primary active" role="button">Неделя</a>
            <a href="?view=month" class="btn btn-primary active" role="button">Месяц</a>
        </div>

        <div class="container task-container">
            <table class="calendar table table-bordered task-todo-container">
                <caption>
                    <fmt:parseDate value="${viewDate}" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" type="date" dateStyle="LONG"/>
                </caption>

                <thead>
                <tr>
                    <th>Просроченные задачи</th>
                    <th>Задачи на сегодня</th>
                    <th>Задачи на завтра</th>
                </tr>
                </thead>
                <tbody>
                <tr>

                    <c:forEach items="${todoTasks}" var="toDotask">
                        <td class="task-card">
                            <c:forEach items="${toDotask.value}" var="task">
                                <div class="${toDotask.key}-task task-card">
                                    <ul>
                                        <li class="finish-date">
                                            Дата: <fmt:formatDate type="date"
                                                                  value="${task.getFinishDate()}"/>
                                        </li>
                                        <li class="finish-time">
                                            Время: <fmt:formatDate type="time" timeStyle="short"
                                                                   value="${task.getFinishDate()}"/>
                                        </li>
                                        <li class="responsible-user">
                                            Ответсвенный: ${task.getResponsibleUser().getName()}
                                        </li>
                                        <li class="task-type">
                                            Тип: ${task.getTaskType().getTypeName()}
                                        </li>
                                        <li class="description">
                                            Текс: ${task.getDescription()}
                                        </li>
                                        <li>
                                            <c:if test="${not empty task.getContact()}">
                                                Контакт: <br/>${task.getContact().getName()}
                                            </c:if>

                                            <c:if test="${not empty task.getDeal()}">
                                                Сделка <br/>${task.getDeal().getName()}
                                            </c:if>

                                            <c:if test="${not empty task.getCompany()}">
                                                Компания <br/>${task.getCompany().getName()}
                                            </c:if>
                                        </li>
                                    </ul>
                                    <form action="/tasks" method="post">
                                        <div class="btn-group">
                                            <input hidden="true" name="taskId" value="${task.getId()}">
                                            <button type="submit" class="btn btn-default" name="changeDate"
                                                    value="onToday">
                                                На сегодня
                                            </button>
                                            <button type="submit" class="btn btn-default" name="changeDate"
                                                    value="onTomorrow">
                                                Завтра
                                            </button>
                                        </div>
                                    </form>

                                </div>
                            </c:forEach>

                        </td>
                    </c:forEach>

                </tr>
                </tbody>
            </table>
        </div>


    </jsp:attribute>
</t:pageLayout>