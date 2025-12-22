<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Successful | RegistrationApp</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card success-card">

    <div class="success-icon">🎉</div>

    <h2>Registration Successful!</h2>
    <p class="subtitle">
        Your account has been created successfully
    </p>

    <div class="success-box">
        <p>
            👤 <b>Username:</b>
            <%= request.getAttribute("username") %>
        </p>
        <p>
            📧 <b>Email:</b>
            <%= request.getAttribute("email") %>
        </p>
    </div>

    <a class="btn" href="<%= request.getContextPath() %>/login.jsp">
        🔐 Go to Login
    </a>

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
