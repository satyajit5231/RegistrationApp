<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verify OTP | RegistrationApp</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card auth-card">

    <h2 class="title">🔐 Verify OTP</h2>
    <p class="subtitle">
        Enter the 6-digit OTP sent to your registered email.
    </p>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/verifyotp" method="post">

        <label>OTP Code</label>
        <input
                type="text"
                name="otp"
                maxlength="6"
                placeholder="Enter OTP"
                required
        >

        <button type="submit" class="btn">
            Verify OTP
        </button>
    </form>

    <div class="helper-links">
        <a href="<%= request.getContextPath() %>/forgot.jsp">
            ← Back to Forgot Password
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
