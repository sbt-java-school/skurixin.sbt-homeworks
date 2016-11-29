<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body>
<span>
    Print count of ${recipeToIngredient.ingredient.name} for ${recipeToIngredient.recipe.name}
</span>
<form:form modelAttribute="recipeToIngredient" method="post" action="/recipes/${recipeToIngredient.recipe.id}">
    <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
    <%--<form:hidden path="id"/>--%>
    <form:hidden path="recipe" value="${recipeToIngredient.recipe.name}"/>
    <form:hidden path="ingredient" value="${recipeToIngredient.ingredient.name}"/>

    <form:input path="count" type="number"  autocomplete="off"/>
    <form:errors path="count" />
    <button type="submit">Edit</button>
</form:form>
</body>
</html>
