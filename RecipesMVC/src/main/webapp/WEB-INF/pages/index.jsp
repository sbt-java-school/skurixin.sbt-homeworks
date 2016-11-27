<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<body>
<ul>
    <c:forEach var="el" items="${list}">
        <li>
            <a href="recipe/${el.id}">
                    ${el.name}
            </a>
            <%--<a href="recipe/${el.id}/edit">--%>
                <%--edit name--%>
            <%--</a>--%>
        </li>
    </c:forEach>
</ul>
</body>
</html>
