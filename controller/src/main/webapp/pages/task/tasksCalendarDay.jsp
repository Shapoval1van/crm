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
            <a href="?view=todo" class="btn btn-primary active" role="button">To-do line</a>
            <a href="?view=day" class="btn btn-primary disabled" role="button">День</a>
            <a href="?view=week" class="btn btn-primary active" role="button">Неделя</a>
            <a href="?view=month" class="btn btn-primary active" role="button">Месяц</a>
        </div>
        <div class="container task-container">
            <table class="calendar table table-bordered table-hover">
                <caption>
                    <a href="?view=day&date=${prevDay}" class="btn btn-info btn-xs active" role="button"><<</a>

                    <fmt:parseDate value="${viewDate}" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" type="date"/>
                    <a href="?view=day&date=${nextDay}" class="btn btn-info btn-xs active" role="button">>></a>
                </caption>
                <thead>
                <tr>
                    <th>Время</th>
                    <th>
                            ${dayName}</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="timeAndTask" items="${tasksForDay}">
                    <tr>
                        <td class="time-in-table">${timeAndTask.key} </td>


                        <c:if test="${empty timeAndTask.value}">
                        <td class=" no-events" rowspan="1"></c:if>

                            <c:if test="${not empty timeAndTask.value}">
                        <td class=" has-events" rowspan="1">
                            <c:forEach items="${timeAndTask.value}" var="task" varStatus="loop">
                                <div class="event">
                                        ${task.getTaskType().getTypeName()},
                                        ${task.getResponsibleUser().getName()},
                                        ${task.getDescription()}
                                </div>
                            </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:pageLayout>