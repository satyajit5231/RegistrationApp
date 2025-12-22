package com.servlet;

import com.dao.UserDAO;
import com.model.User;
import com.util.DBConnect;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect(req.getContextPath()+"/login.jsp");
            return;
        }

        try {
            UserDAO dao = new UserDAO(DBConnect.getConn());

            int userId = (int) session.getAttribute("userId");

            // ✅ logged-in user
            User user = dao.getUserById(userId);

            // ✅ all users
            req.setAttribute("user", user);
            req.setAttribute("users", dao.getAllUsers());

            req.getRequestDispatcher("/WEB-INF/views/users.jsp")
                    .forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to load users");
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp")
                    .forward(req, res);
        }
    }
}
