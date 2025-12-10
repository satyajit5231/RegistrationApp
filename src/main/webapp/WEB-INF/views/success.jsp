<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Successful</title>
</head>
<body>

<h2>Registration Successful! 🎉</h2>

<p>
    Welcome,
    <strong><%= request.getAttribute("username") %></strong>
</p>

<p>
    Your email:
    <strong><%= request.getAttribute("email") %></strong>
</p>

<p>
    Now you can
    <a href="login.jsp">Login here</a>.
</p>

</body>
</html>
