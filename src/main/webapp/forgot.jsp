<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password | RegistrationApp</title>

    <!-- Reuse common CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card">
    <h2>Forgot Password 🔐</h2>
    <p class="subtitle">
        Enter your registered email to receive an OTP
    </p>

    <!-- ❌ Error message -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error"><%= error %></div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/sendotp" method="post">

        <div class="input-group">
            <label>Email Address</label>
            <input
                type="email"
                name="email"
                placeholder="Enter your registered email"
                required
            >
        </div>

        <button class="btn">Send OTP</button>
    </form>

    <div class="link">
        <a href="<%= request.getContextPath() %>/login.jsp">
            ← Back to Login
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
