package com.servlet;

import com.util.DBConnect;
import com.model.User;
import com.dao.UserDAO;
import com.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.getWriter().println("REGISTER SERVLET IS MAPPED");
        System.out.println("REGISTER GET HIT");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");


        // ✅ Basic validation
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/register.jsp").forward(req, res);
            return;
        }

        // ✅ Strong password validation (Day-9 standard)
        if (!PasswordUtil.isStrong(password)) {
            req.setAttribute("error", "Password must be strong (8 chars, upper, number, special).");
            req.getRequestDispatcher("/register.jsp").forward(req, res);
            return;
        }

        UserDAO dao;
        try {
            dao = new UserDAO(DBConnect.getConn());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ✅ Check duplicate username or email
        if (dao.exists(username, email)) {
            req.setAttribute("error", "Username or email already exists.");
            req.getRequestDispatcher("/register.jsp").forward(req, res);
            return;
        }

        // ✅ Hash password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(hashedPassword);

        boolean success = dao.register(u);

        if (success) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            // ✅ Forward to protected success page
            req.getRequestDispatcher("/WEB-INF/views/success.jsp")
                    .forward(req, res);
        } else {
            req.setAttribute("error", "Registration failed due to server error.");
            res.sendRedirect(req.getContextPath() + "/register.jsp");
        }
    }
}
