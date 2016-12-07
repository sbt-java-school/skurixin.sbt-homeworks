<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Изменить количество</title>
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
<span>
    Введите количество ${recipeToIngredient.ingredient.name} для рецепта ${recipeToIngredient.recipe.name}
</span>
    <form:form modelAttribute="recipeToIngredient" id="recipe_form" class="form" method="post"
               action="/recipes/${recipeToIngredient.recipe.id}">
        <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
        <%--<form:hidden path="id"/>--%>
        <form:hidden path="recipe" value="${recipeToIngredient.recipe.name}"/>
        <form:hidden path="ingredient" value="${recipeToIngredient.ingredient.name}"/>

        <ul id="form_list">
            <form:input path="count" type="number" autocomplete="off"/>
            <form:errors path="count"/>
        </ul>
        <input id="send" type="submit" value="Изменить">
    </form:form>
</div>
</body>
</html>
