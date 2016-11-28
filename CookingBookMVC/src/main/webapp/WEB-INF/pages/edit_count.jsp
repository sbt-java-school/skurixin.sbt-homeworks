<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<span>
    Print count of ${recipeToIngredient.ingredient.name} for ${recipeToIngredient.recipe.name}
</span>
<body>
<form:form modelAttribute="recipeToIngredient" method="post" action="/recipes/${recipeToIngredient.recipe.id}">
    <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
    <form:hidden path="id"/>
    <form:input path="count" type="number" />
    <button type="submit">Edit</button>
</form:form>
</body>
</html>
