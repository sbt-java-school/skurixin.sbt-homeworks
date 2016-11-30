<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>${recipeCurrent.name} рецепт</title>
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
        <form:form action="/recipes/${recipeCurrent.id}" class="form" modelAttribute="recipeToIngredient" method="post"
                   autocomplete="off">
            <form:hidden path="id"/>
            <%--<form:select path="ingredient" items="${listNotContains}"/>--%>

            <%--<form:select path="ingredient">--%>
            <%--<c:forEach var="ingredientVar" items="${listNotContains}">--%>
            <%--<form:option value="${ingredientVar.name}">${ingredientVar.name}</form:option>--%>
            <%--</c:forEach>--%>
            <%--</form:select>--%>

            <ul id="form_list">
                <li>
                    <form:input type="text" list="help_list" path="ingredient" autocomplete="off"/>
                    <datalist id="help_list">
                        <c:forEach var="ingredientName" items="${ingredientsNotContainedInRecipe}">
                            <option<%-- value="${ingredient}"--%>>${ingredientName.name}</option>
                        </c:forEach>
                    </datalist>
                </li>
                <li>
                    <form:input path="count" type="number"/>
                </li>
                <form:errors path="count"/>
            </ul>
            <form:errors path="ingredient"/>
            <input id="send" type="submit" value="Добавить">
        </form:form>
    </aside>

    <div class="content">
        <h3 id="basket_content_head">
            <span>${recipeCurrent.name}</span>
        </h3>
        <br>
        <h3 class="content_head">
            <span>${recipeCurrent.description}</span>
        </h3>
        <ul id="basket_list">
            <c:forEach var="instance" items="${ingredientsExistingInRecipe}">
                <li class="basket_item">
                    <ul class="basket_inner">
                        <li class="ingredient">
                            <span class="description">
                                    ${instance.ingredient.name}
                            </span>
                        </li>
                        <li class="count">
                                ${instance.count}
                        </li>
                        <li>
                            <a class="basket_delete_thing"
                               href="/recipes/${recipeCurrent.id}/ingredient/${instance.ingredient.id}/delete">
                                Удалить связь
                            </a>
                        </li>
                        <li class="edit_fields">
                            <a href="/recipes/ingredient/${instance.ingredient.id}/rename">
                                Переименовать ингредиент
                            </a>
                        </li>
                        <li class="edit_fields">
                            <a href="/recipes/${recipeCurrent.id}/ingredient/${instance.ingredient.id}/edit">
                                Изменить количество
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
