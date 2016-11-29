<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body>

<span>${recipeCurrent.name}</span>
<br>
<span>${recipeCurrent.description}</span>
<br>
<%--<a href="/recipes/${recipeCurrent}/add">Add ingredient to recipe</a>--%>
<%--<br>--%>
<ul>
    <c:forEach var="instance" items="${ingredientsExistingInRecipe}">
        <li>
            <span>
                ${instance.ingredient.name} - ${instance.count}
            </span>

            <a href="/recipes/${recipeCurrent.id}/ingredient/${instance.ingredient.id}/edit">
                edit count
            </a>

            <a href="/recipes/${recipeCurrent.id}/ingredient/${instance.ingredient.id}/delete">
                delete
            </a>
        </li>
    </c:forEach>
</ul>

<form:form action="/recipes/${recipeCurrent.id}" modelAttribute="recipeToIngredient" method="post" autocomplete="off">
    <form:hidden path="id"/>
    <%--<form:select path="ingredient" items="${listNotContains}"/>--%>

    <%--<form:select path="ingredient">--%>
        <%--<c:forEach var="ingredientVar" items="${listNotContains}">--%>
            <%--<form:option value="${ingredientVar.name}">${ingredientVar.name}</form:option>--%>
        <%--</c:forEach>--%>
    <%--</form:select>--%>

    <form:input type="text" list="help_list" path="ingredient" autocomplete="off"/>
    <datalist id="help_list">
        <c:forEach var="ingredientName" items="${ingredientsNotContainedInRecipe}">
            <option<%-- value="${ingredient}"--%>>${ingredientName.name}</option>
        </c:forEach>
    </datalist>

    <form:input path="count" type="number"/>
    <form:errors path="ingredient"/>
    <form:errors path="count"/>
    <button type="submit">Add</button>
</form:form>

</body>
</html>
