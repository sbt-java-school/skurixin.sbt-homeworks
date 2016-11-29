<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<body>

<a href="/recipes/add">
    Добавить рецепт
</a>
<br>
<%--<sf:form method="POST" modelAttribute="recipe">--%>
    <%--<sf:input path="name"/>--%>
    <%--<sf:input path="description"/>--%>
<%--</sf:form>--%>

<ul>
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
