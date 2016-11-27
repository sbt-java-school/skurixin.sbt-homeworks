<%@ page isELIgnored="false" %>
Hello from accounts

${message}
<p>
<form method="POST" action="/spring-mvc/accounts/save">

account <input type="text" name="accountId" value="${accountId}"/>

<button type="submit" value="Save">Save</button>
</form>
</p>