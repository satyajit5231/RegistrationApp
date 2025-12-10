<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>

<h2>User Registration</h2>

<% String err = (String) request.getAttribute("error");
   if (err != null) { %>
    <p style="color:red;"><%= err %></p>
<% } %>

<form action="register" method="post">
    Full Name: <input type="text" name="name"><br><br>
    Username:  <input type="text" name="username"><br><br>
    Email:     <input type="text" name="email"><br><br>
    Password:  <input type="password" name="password"><br><br>

    <input type="submit" value="Register"><br><br>

    <p>
        Already registered?
        <a href="login.jsp">Login here</a>
    </p>
</form>

</body>
</html>
