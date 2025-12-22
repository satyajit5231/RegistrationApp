<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password | RegistrationApp</title>

    <!-- Common CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card">
    <h2>Reset Password 🔐</h2>
    <p class="subtitle">
        Create a new strong password for your account
    </p>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error"><%= error %></div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/reset" method="post">

        <label>New Password</label>
        <input type="password" name="newpass" required
               placeholder="Enter new password">

        <label>Confirm Password</label>
        <input type="password" name="cpass" required
               placeholder="Re-enter new password">

        <button class="btn" type="submit">
            🔄 Update Password
        </button>
    </form>

    <div class="footer-link">
        <a href="<%= request.getContextPath() %>/login.jsp">
            Back to Login
        </a>
    </div>
</div>
<script>
    // Load saved theme
    if (localStorage.getItem("theme") === "dark") {
        document.body.classList.add("dark");
    }

    function toggleTheme() {
        document.body.classList.toggle("dark");

        if (document.body.classList.contains("dark")) {
            localStorage.setItem("theme", "dark");
        } else {
            localStorage.setItem("theme", "light");
        }
    }
</script>


</body>
</html>
