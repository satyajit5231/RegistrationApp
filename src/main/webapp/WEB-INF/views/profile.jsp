<%@ page import="com.UserDAO" %>
<%@ page import="com.User" %>
<%@ page import="com.DBConnect" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    // SESSION CHECK
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = (int) sess.getAttribute("userId");

    // FETCH USER FROM DATABASE
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

<p><b>User ID:</b> <%= u.getId() %></p>
<p><b>Name:</b> <%= u.getName() %></p>
<p><b>Email:</b> <%= u.getEmail() %></p>
<p><b>Username:</b> <%= u.getUsername() %></p>

<br>
<a href="change-password.jsp">Change Password</a>
<a href="logout">Logout</a>

</body>
</html>
