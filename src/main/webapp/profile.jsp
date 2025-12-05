<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession sess = request.getSession(false);

    if (sess == null || sess.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String username = (String) sess.getAttribute("username");
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>User Profile</title>
</head>
<body>

<h2>Welcome, <%= username %></h2>
<p>This is your profile page.</p>

<br>
<a href="logout">Logout</a>

</body>
</html>
