<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Изменить ингредиент</title>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<form:form modelAttribute="ingredientFORM" method="post" autocomplete="off">
    <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
    <form:hidden path="id"/>
    <form:input path="name" autocomplete="off"/>
    <form:errors path="name"/><br>
    <button type="submit">Add</button>
</form:form>
</body>
</html>
