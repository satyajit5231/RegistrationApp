<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    // Block direct access without login
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
</head>
<body>

<h2>Change Password</h2>

<form action="change-password" method="post">

    Old Password:
    <input type="password" name="oldPassword" required><br><br>

    New Password:
    <input type="password" name="newPassword" required><br><br>

    Confirm Password:
    <input type="password" name="confirmPassword" required><br><br>

    <input type="submit" value="Update Password">
</form>

</body>
</html>
