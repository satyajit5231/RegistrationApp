<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.UserDAO" %>
<%@ page import="com.DBConnect" %>
<%@ page import="com.User" %>

<%
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = (int) sess.getAttribute("userId");

    UserDAO dao = new UserDAO(DBConnect.getConn());
    User u = dao.getUserById(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>

<h2>Welcome, <%= u.getName() %> 👋</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <td><%= u.getId() %></td>
    </tr>
    <tr>
        <th>Name</th>
        <td><%= u.getName() %></td>
    </tr>
    <tr>
        <th>Email</th>
        <td><%= u.getEmail() %></td>
    </tr>
    <tr>
        <th>Username</th>
        <td><%= u.getUsername() %></td>
    </tr>
</table>

<br>
<a href="change-password.jsp">Change Password</a><br><br>
<a href="logout">Logout</a>

</body>
</html>
