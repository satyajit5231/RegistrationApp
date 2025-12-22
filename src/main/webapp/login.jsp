<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | RegistrationApp</title>

    <!-- Global CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card">
    <h2>Welcome Back 👋</h2>

    <!-- ✅ Password reset success -->
    <%
        String reset = request.getParameter("reset");
        if ("success".equals(reset)) {
    %>
        <div class="success">
            ✅ Password reset successful. Please login again.
        </div>
    <%
        }
    %>

    <!-- ❌ Login error -->
    <%
        String err = (String) request.getAttribute("error");
        if (err != null) {
    %>
        <div class="error"><%= err %></div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/login" method="post">

        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" placeholder="Enter username" required>
        </div>

        <div class="input-group">
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password" required>
        </div>

        <button class="btn">Login</button>
    </form>

    <div class="link">
        <p>
            Don’t have an account?
            <a href="<%= request.getContextPath() %>/register.jsp">Register</a>
        </p>
        <p>
            <a href="<%= request.getContextPath() %>/forgot.jsp">
                Forgot password?
            </a>
        </p>
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
