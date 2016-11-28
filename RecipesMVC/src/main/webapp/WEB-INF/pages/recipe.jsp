<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<body>
<ul>
    <c:forEach var="instance" items="${ingredients}">
        <li>
            <span>
                ${instance.ingredient.name} - ${instance.count}
            </span>

            <%--<a href="ingredient/${instance.ingredient.id}/delete?recipe_id=${recipe_id}">--%>
                <%--delete--%>
            <%--</a>--%>
        </li>
    </c:forEach>
</ul>
</body>
</html>
