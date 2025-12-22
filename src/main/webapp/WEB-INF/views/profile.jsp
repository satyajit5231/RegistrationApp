<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard | RegistrationApp</title>

    <!-- Common CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
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
        <h2>Error ⚠️</h2>
        <div class="error">User data not found. Please login again.</div>
        <a class="btn" href="<%= request.getContextPath() %>/login.jsp">
            Go to Login
        </a>
    </div>
<%
        return;
    }
%>

<div class="card">
    <h2>Welcome, <%= u.getName() %> 👋</h2>
    <p class="subtitle">
        This is your personal dashboard
    </p>

    <div class="profile-box">
        <div class="profile-row">
            <span>User ID</span>
            <span><%= u.getId() %></span>
        </div>

        <div class="profile-row">
            <span>Full Name</span>
            <span><%= u.getName() %></span>
        </div>

        <div class="profile-row">
            <span>Username</span>
            <span><%= u.getUsername() %></span>
        </div>

        <div class="profile-row">
            <span>Email</span>
            <span><%= u.getEmail() %></span>
        </div>
    </div>

    <div class="action-buttons">
        <a class="btn secondary" href="<%= request.getContextPath() %>/change-password">
            🔒 Change Password
        </a>

        <a class="btn danger" href="<%= request.getContextPath() %>/logout">
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
