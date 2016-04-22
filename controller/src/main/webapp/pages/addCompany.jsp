<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Добавление компании">
    <jsp:attribute name="bodyLayout">
<div class="container">
    <h1 class="page-header text-center">Добавление компании</h1>
    <form class="form-horizontal" action="/addCompany" method="post">
        <div class="row">

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">Поля компании</h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="companyName">Название компании</label>
                            <input type="text" class="form-control" id="companyName" name="companyName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyTags">Теги</label>
                            <input type="text" class="form-control" id="companyTags" name="companyTags">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyResponsible">Ответственный</label>
                            <select class="form-control" id="companyResponsible" name="companyResponsible">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${usersList}" var="user">
                                    <option value=${user.id}>${user.name}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactPhoneType">Номер телефона</label>
                            <select class="form-control" id="companyPhoneType" name="companyPhoneType">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${phoneTypes}" var="phoneType">
                                    <option value=${phoneType}>${phoneType}</option>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control" id="companyPhone" name="companyPhone"
                                   style="margin-top: 10px">
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="companyEmail">Email</label>
                            <input type="email" class="form-control" id="companyEmail" name="companyEmail">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyNote">Примечания</label>
                            <textarea class="form-control" id="companyNote" name="companyNote" rows="3"></textarea>
                        </fieldset>
                    </div>
                </div>
            </div>

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">Добавить контакт</h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="contactName">Имя фамилия</label>
                            <input type="text" class="form-control" id="contactName" name="contactName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactPosition">Должность</label>
                            <input type="text" class="form-control" id="contactPosition" name="contactPosition">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactPhoneType">Телефон</label>
                            <select class="form-control" id="contactPhoneType" name="contactPhoneType">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${phoneTypes}" var="phoneType">
                                    <option value=${phoneType}>${phoneType}</option>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control" id="contactPhone" name="contactPhone"
                                   style="margin-top: 10px">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactEmail">Email</label>
                            <input type="email" class="form-control" id="contactEmail" name="contactEmail">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactSkype">Skype</label>
                            <input type="text" class="form-control" id="contactSkype" name="contactSkype">
                        </fieldset>
                    </div>
                </div>
            </div>

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">Добавить Сделку</h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="dealName">Название сделки</label>
                            <input type="text" class="form-control" id="dealName" name="dealName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="dealStage">Этап</label>
                            <select class="form-control" id="dealStage" name="dealStage">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${dealStages}" var="stage">
                                    <option value=${stage}>${stage}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="dealBudget">Сумма</label>
                            <input type="text" class="form-control" id="dealBudget" name="dealBudget">
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title">Запланировать действие</h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="taskPeriod">Когда</label>
                            <select class="form-control" id="taskPeriod" name="taskPeriod">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${taskPeriods}" var="period">
                                    <option value=${period}>${period}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control"  value="dd.mm.yyyy" id="taskDate" name="taskDate">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                            </div>
                            <div class="input-group clockpicker" style="margin-top: 10px">
                                <input type="text" class="form-control" value="hh:mm" id="taskTime" name="taskTime">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    $('#taskDate').data("DateTimePicker").FUNCTION();
                                });
                            </script>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="taskResponsible">Ответственный</label>
                            <select class="form-control" id="taskResponsible" name="taskResponsible">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${usersList}" var="user">
                                    <option value=${user.id}>${user.name}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyNote">Описание задачи</label>
                            <textarea class="form-control" id="taskDescription" name="taskDescription"
                                      rows="3"></textarea>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <fieldset class="form-group" style="margin-left: 50px; margin-top: 30px">
                <label id="CompanyErrorMessage"class="label-danger"></label>
            </fieldset>
        </div>
        <div class="row" style="text-align:center;">
            <div class="col-md-12 col-sm-12">
                <button type="submit" class="btn btn-success btn-large" onclick="return checkAddCompanyForm()">
                    Сохранить
                </button>
                <button type="reset" class="btn btn-danger btn-large" style="margin-left: 40px">Отмена</button>
            </div>
        </div>
    </form>
    </jsp:attribute>
</t:pageLayout>