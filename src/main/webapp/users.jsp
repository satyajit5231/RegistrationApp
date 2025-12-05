<%@ page import="java.util.*, com.User" %>

<%
    // Protect the page: only logged-in users should access this
    jakarta.servlet.http.HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Registered Users</title>
</head>
<body>

<h2>Registered Users</h2>
<hr>

<%
    // Get user list from application scope
    List<User> users = (List<User>) application.getAttribute("users");

    if (users == null || users.isEmpty()) {
        out.println("<p>No users registered yet.</p>");
    } else {
        for (User u : users) {
            out.println("Username: " + u.getUsername() +
                        " | Email: " + u.getEmail() + "<br>");
        }
    }
%>

<br><br>
<a href="profile.jsp">Go to Profile</a><br>
<a href="logout">Logout</a>

</body>
</html>
