<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Главная страница</title>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>

<a href="/recipes/add">
    Добавить рецепт
</a>

<form method="post" action="/recipes">
    <span>Поиск рецепта</span><br>
    <input name="recipeName" type="text" id="recipeName" value="" ><br>
    <input type="submit"><br>
</form>

<br>

<ul>
    <h3>${searchMessage}</h3><br>
    <c:forEach var="el" items="${list}">
        <li>
            <a href="/recipes/${el.id}">
                    ${el.name}
            </a>
            <a href="/recipes/${el.id}/delete">
                Удалить
            </a>
            <a href="/recipes/${el.id}/edit">
                Изменить
            </a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
