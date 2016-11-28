<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<body>
<form:form modelAttribute="recipeFORM" method="post" action="/recipes">
    <%--<input id="id" name="id" type="hidden" value="${recipeFORM.id}"/>--%>
    <form:hidden path="id"/>
    <form:input path="name"/>
    <form:input path="description"/>
    <button type="submit">Add</button>
</form:form>
</body>
</html>
