<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile | RegistrationApp</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<%
    com.model.User u = (com.model.User) request.getAttribute("user");
    if (u == null) {
%>
    <div class="card">
        <p class="error">User session expired. Please login again.</p>
        <a class="btn" href="<%= request.getContextPath() %>/login.jsp">Go to Login</a>
    </div>
<%
        return;
    }
%>

<div class="card profile-card">

    <div class="profile-header">
        <div class="avatar">
            <%= u.getName().substring(0,1).toUpperCase() %>
        </div>
        <div>
            <h2><%= u.getName() %></h2>
            <p class="subtitle">@<%= u.getUsername() %></p>
        </div>
    </div>

    <div class="profile-info">
        <div class="info-row">
            <span>User ID</span>
            <span><%= u.getId() %></span>
        </div>
        <div class="info-row">
            <span>Email</span>
            <span><%= u.getEmail() %></span>
        </div>
        <div class="info-row">
            <span>Username</span>
            <span><%= u.getUsername() %></span>
        </div>
    </div>

    <div class="profile-actions">
        <a class="btn-outline" href="<%= request.getContextPath() %>/change-password">
            🔐 Change Password
        </a>
        <a class="btn-danger" href="<%= request.getContextPath() %>/logout">
            🚪 Logout
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
