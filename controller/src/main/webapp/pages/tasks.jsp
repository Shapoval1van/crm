<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Задачи">
    <jsp:attribute name="bodyLayout">
        <p class="task-view-links">
            <a href="#" class="btn btn-primary disabled" role="button">To-do line</a>
            <a href="#" class="btn btn-primary disabled" role="button">День</a>
            <a href="#" class="btn btn-primary disabled" role="button">Неделя</a>
            <a href="#" class="btn btn-primary disabled" role="button">Месяц</a>
            <a href="#" class="btn btn-primary active" role="button">Список</a>
        </p>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Дата исполнения</th>
        <th>Время</th>
        <th>Ответственный</th>
        <th>Контакт\сделка</th>
        <th>Тип зададачи</th>
        <th>Текст зададачи</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="task">
        <tr>
            <td class="finish-date"><fmt:formatDate type="date"
                                                    value="${task.getFinishDate()}"/></td>
            <td class="finish-time"><fmt:formatDate type="time" timeStyle="short"
                                                    value="${task.getFinishDate()}"/></td>
            <td class="responsible-user">${task.getResponsibleUser().getName()}</td>

            <c:if test="${not empty task.getContact()}">
                <td class="contact-company-deal">Contact <br/>${task.getContact().getName()}</td>
            </c:if>

            <c:if test="${not empty task.getDeal()}">
                <td class="contact-company-deal">Deal <br/>${task.getDeal().getName()}</td>
            </c:if>

            <c:if test="${not empty task.getCompany()}">
                <td class="contact-company-deal">Company <br/>${task.getCompany().getName()}</td>
            </c:if>

            <td class="task-type">${task.getTaskType().getTypeName()}</td>
            <td class="description">${task.getDescription()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>