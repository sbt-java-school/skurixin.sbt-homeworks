<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Главная страница</title>
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

<div class="layout-positioner main_class">

    <aside>
        <form class="form" method="post" action="/recipes">
            <em>Поиск рецепта</em><br>
            <ul id="form_list">
                <li>
                    <input name="recipeName" type="text" id="recipeName" value="" autocomplete="off">
                </li>
            </ul>
            <input id="send" type="submit" value="Искать">
        </form>
    </aside>

    <div class="content">
        <a id="basket_content_head" href="/recipes/add">
            Добавить рецепт
        </a>
        <br>

        <h3 class="content_head">
            ${searchMessage}
        </h3>
        <ul id="basket_list">

            <c:forEach var="el" items="${list}">
                <li class="basket_item">
                    <ul class="basket_inner">
                        <li>
                            <a class="description" href="/recipes/${el.id}">
                                <em>
                                        ${el.name}
                                </em>
                            </a>
                        </li>
                        <li class="editor">
                            <a href="/recipes/${el.id}/edit">
                                Изменить
                            </a>
                        </li>
                        <li>
                            <a class="basket_delete_thing" href="/recipes/${el.id}/delete">
                                Удалить
                            </a>
                        </li>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
