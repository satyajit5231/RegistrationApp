<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | RegistrationApp</title>

    <!-- Reuse same CSS as login -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<button class="theme-toggle" onclick="toggleTheme()">
    🌙 / ☀️
</button>


<div class="card">
    <h2>Create Account ✨</h2>
    <p class="subtitle">Join us and get started</p>

    <!-- ❌ Error message -->
    <%
        String err = (String) request.getAttribute("error");
        if (err != null) {
    %>
        <div class="error"><%= err %></div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/register" method="post">

        <div class="input-group">
            <label>Full Name</label>
            <input type="text" name="name" placeholder="Enter full name" required>
        </div>

        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" placeholder="Choose a username" required>
        </div>

        <div class="input-group">
            <label>Email</label>
            <input type="email" name="email" placeholder="Enter email address" required>
        </div>

        <div class="input-group">
            <label>Password</label>
            <input type="password" name="password" placeholder="Create a strong password" required>
        </div>

        <button class="btn">Register</button>
    </form>

    <div class="link">
        <p>
            Already registered?
            <a href="<%= request.getContextPath() %>/login.jsp">
                Login here
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
