<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Login Form</h2>

<% String err = (String) request.getAttribute("error");
   if (err != null) { %>
    <p style="color:red;"><%= err %></p>
<% } %>

<form action="login" method="post">
    Username: <input type="text" name="username"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="submit" value="Login"><br><br>

    <p>
        Don't have an account?
        <a href="register.jsp">Register here</a>

    </p>
</form>

</body>
</html>
