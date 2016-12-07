<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Изменить ингредиент</title>
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<header>
    <div class="layout-positioner">
        <ul id="list_grey">
            <li>
                <a href="/recipes">На главную</a>
            </li>
        </ul>
    </div>
</header>

<div class="layout-positioner">
    <h3>
        Введите название ингредиента
    </h3>
    <form:form modelAttribute="ingredientFORM" id="recipe_form" class="form" method="post" autocomplete="off">
        <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
        <form:hidden path="id"/>
        <ul id="form_list">
            <form:input path="name" autocomplete="off"/>
            <form:errors path="name"/><br>
        </ul>
        <input id="send" type="submit" value="Изменить">
    </form:form>
</div>
</body>
</html>
