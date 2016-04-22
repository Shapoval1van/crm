<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Создать контакт">
    <jsp:attribute name="bodyLayout">
                <div class="container">
                    <div class="row">
                        <form class="form-horizontal"  action="/contact" method="post" id="create-contact"  enctype="multipart/form-data">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="name" class="col-md-4 control-label">Имя Фамилия</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="name" name="contact-name">
                                    </div>
                                </div> <!--name-->

                                <div class="form-group">
                                    <label for="tags" class="col-md-4 control-label">Теги</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="tags" id="tags">
                                    </div>
                                </div><!--tags-->

                                <div class="form-group">
                                    <label for="responsible" class="col-md-4 control-label">Ответственный</label>
                                    <div class="col-md-6 col-md-offset-2">
                                        <select class="form-control" name="responsible" id ="responsible">
                                            <c:forEach items="${users}" var="user">
                                                <option value=${user.getId()}>${user.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div><!--responsible-->

                                <div class="form-group">
                                    <label for="position" class="col-md-4 control-label">Должность</label>
                                    <div class="col-md-6 col-md-offset-2">
                                        <input type="text" class="form-control" name="position" id="position">
                                    </div>
                                </div><!--position-->

                                <div class="form-group">
                                    <label for="phone-type" class="col-md-4 control-label">Номер телефона</label>
                                    <div class="col-md-4">
                                        <select class="form-control" name="phone-type" id = "phone-type">
                                            <option value="1">рабочий телефон</option>
                                            <option value="2">рабочий прямой</option>
                                            <option value="3">мобильный</option>
                                            <option value="4">факс</option>
                                            <option value="5">домашний</option>
                                            <option value="6">другой</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <input class="form-control" type="text" name="phone_number">
                                    </div>
                                </div><!--phone-->

                                <div class="form-group">
                                    <label for="email" class="col-md-4 control-label">Email</label>
                                    <div class="col-md-6 col-md-push-2">
                                        <input type="email" class="form-control" name="email" id="email">
                                    </div>
                                </div><!--Email-->

                                <div class="form-group">
                                    <label for="skype" class="col-sm-4 control-label">Skype</label>
                                    <div class="col-md-6 col-md-offset-2">
                                        <input type="text" class="form-control" name="skype" id="skype">
                                    </div>
                                </div><!--Skype-->

                                <div class="form-group">
                                    <label for="note" class="col-md-4 control-label">Примечание</label>
                                    <div class="col-md-8">
                                        <textarea class="form-control" name="note" rows="4" cols="25" id="note"></textarea>
                                    </div>
                                </div><!--note-->

                                <div class="form-group">
                                    <div class="col-md-1 col-md-push-3"><input type="file" name="file"  multiple name="input"></div>
                                </div><!--file-->

                            </div><!--left-->

                            <div class="col-md-6">
                                    <p class="prompt">Запланировать действие</p><hr class="divider-customer">
                                    <div class="block">
                                        <div class="form-group">
                                            <label  for="period" class="col-md-4 control-label">Период:</label>
                                            <div class="col-md-4">
                                                <select class="form-control" name="task-period" id = "period">
                                                    <option value="1">Сегодня</option>
                                                    <option value="2">Все дни</option>
                                                    <option value="3">Завтра</option>
                                                    <option value="4">Следующая неделя</option>
                                                    <option value="5">Следующий месяц</option>
                                                    <option value="6">Следующий год</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="date" name="task-date" id="task-date" class="form-control">
                                            </div>
                                        </div><!--period-->

                                        <div class="form-group">
                                            <label for="task-responsible" class="col-md-4 control-label">Ответственый</label>
                                            <div class="col-md-6 col-md-offset-2">
                                                <select class="form-control" name="task-responsible" id ="task-responsible">
                                                    <c:forEach items="${users}" var="user">
                                                        <option value=${user.getId()}>${user.getName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div><!--responsible-task-->

                                        <div class="form-group">
                                            <label for="task-type" class="col-md-4 control-label">Тип задачи</label>
                                            <div class="col-md-6 col-md-offset-2">
                                                <select class="form-control" name="task-type" id ="task-type">
                                                    <c:forEach items="${taskTypes}" var="taskType">
                                                        <option value=${taskType.getId()}>${taskType.getTypeName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div><!--task-description-->

                                        <div class="form-group">
                                            <label for="task-description" class="col-md-4 control-label">Текст задачи</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="task-description" id = "task-description">
                                            </div>
                                        </div><!--task-description-->
                                    </div>

                                    <p class="prompt">Быстрое добавление сделки</p><hr class="divider-customer">
                                    <div class="block">
                                        <div class="form-group">
                                            <label  for="deal-name" class="col-md-4 control-label">Название сделки:</label>
                                            <div class="col-md-8">
                                                <input type="text" id="deal-name" name="deal-name" class="form-control">
                                            </div>
                                        </div><!--deal-name-->

                                        <div class="form-group">
                                            <label for="deal-stage" class="col-md-4 control-label">Этап:</label>
                                            <div class="col-md-6 col-md-offset-2">
                                                <select class="form-control" name="deal-stage" id = "deal-stage">
                                                    <option value="1">Сегодня</option>
                                                    <option value="2">Все дни</option>
                                                    <option value="3">Завтра</option>
                                                    <option value="4">Следущая неделя</option>
                                                    <option value="5">Следущий месяц</option>
                                                    <option value="6">Следущий год</option>
                                                </select>
                                            </div>
                                        </div><!--deal-stage-->

                                        <div class="form-group">
                                            <label for="budget" class="col-md-4 control-label">Бюджет</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control"  name ="budget" id = "budget">
                                            </div>
                                        </div><!--budget-->
                                    </div>

                                    <p class="prompt">Выбрать компанию</p><hr class="divider-customer">
                                    <div class="row block">
                                        <div class="col-md-4 ">
                                            <label class="radio">
                                                <input type="radio" name="radio" id="choose-company" value="choose">
                                                Выбрать компанию
                                            </label>
                                            <label class="radio">
                                                <input type="radio" name="radio" id="crate-company" value="create" checked>
                                                Создать компанию
                                            </label>
                                        </div>
                                        <div class="col-md-6 col-md-offset-1">
                                            <select class="form-control" name="select_company"  id ="deal" disabled>
                                                <option value="0"></option>
                                            <c:forEach items="${companies}" var="company">
                                                <option value=${company.getId()}>${company.getName()}</option>
                                            </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="block">
                                        <div class="form-group">
                                            <label for="name-company" class="col-md-4 control-label">Название</label>
                                            <div class="col-md-8">
                                                <input type="text"  name="name-company" class="form-control" id="name-company">
                                            </div>
                                        </div> <!--name-->

                                        <div class="form-group">
                                            <label for="phone-company" class="col-md-4 control-label">Телефон</label>
                                            <div class="col-md-6 col-md-offset-2">
                                                <input type="text" class="form-control" name="phone-company" id="phone-company">
                                            </div>
                                        </div><!--phone-->

                                        <div class="form-group">
                                            <label for="email-company" class="col-md-4 control-label">Email</label>
                                            <div class="col-md-6 col-md-offset-2">
                                                <input type="email" name="email-company" class="form-control" id="email-company">
                                            </div>
                                        </div><!--Email-->

                                        <div class="form-group">
                                            <label for="web-company" class="col-md-4 control-label">WEB адрес</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="web-company" id="web-company">
                                            </div>
                                        </div><!--web-->

                                        <div class="form-group">
                                            <label for="note-company" class="col-md-4 control-label">Примечание</label>
                                            <div class="col-md-8">
                                                <textarea class="form-control" rows="2" cols="25" name="note-company" id="note-company"></textarea>
                                            </div>
                                        </div><!--note-->

                                    </div>
                            </div><!--right-->

                            <div class="col-md-offset-4">
                                <button type="submit" class="btn btn-default">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>

    </jsp:attribute>
</t:pageLayout>