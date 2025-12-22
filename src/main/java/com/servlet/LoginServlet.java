package com.servlet;

import com.util.DBConnect;
import com.dao.UserDAO;
import com.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDAO dao = new UserDAO(DBConnect.getConn());
            String storedPassword = dao.getPasswordByUsername(username);

            boolean authenticated = false;

            if (storedPassword != null) {

                if (storedPassword.startsWith("$2a$")
                        || storedPassword.startsWith("$2b$")
                        || storedPassword.startsWith("$2y$")) {

                    authenticated = BCrypt.checkpw(password, storedPassword);

                } else {
                    authenticated = password.equals(storedPassword);
                    if (authenticated) {
                        String newHash = BCrypt.hashpw(password, BCrypt.gensalt());
                        dao.updatePasswordByUsername(username, newHash);
                    }
                }
            }

            if (authenticated) {

                // 🔐 session
                HttpSession session = req.getSession();
                int userId = dao.getUserIdByUsername(username);
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                session.setMaxInactiveInterval(15 * 60);

                // ✅ LOAD USER OBJECT FOR JSP
                User user = dao.getUserById(userId);
                req.setAttribute("user", user);

                req.getRequestDispatcher("/WEB-INF/views/profile.jsp")
                        .forward(req, res);

            } else {
                req.setAttribute("error", "Invalid username or password");
                req.getRequestDispatcher("/login.jsp")
                        .forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Login failed. Try again later.");
        }
    }
}
