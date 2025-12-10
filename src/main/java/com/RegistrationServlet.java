package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // ✅ Basic validation
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, res);
            return;
        }

        if (password.length() < 6) {
            req.setAttribute("error", "Password must be at least 6 characters.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, res);
            return;
        }

        UserDAO dao = new UserDAO(DBConnect.getConn());

        // ✅ Check duplicate username or email
        if (dao.exists(username, email)) {
            req.setAttribute("error", "Username or email already exists.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, res);
            return;
        }

        // ✅ Hash password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Generated Hash: " + hashedPassword);


        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(hashedPassword); // store hash only

        boolean success = dao.register(u);

        if (success) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "Registration failed due to server error.");
            req.getRequestDispatcher("/WEB=INF/views/register.jsp").forward(req, res);
        }
    }
}
