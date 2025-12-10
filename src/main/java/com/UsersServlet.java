package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession sess = req.getSession(false);

        if (sess == null || sess.getAttribute("userId") == null) {
            res.sendRedirect("login");
            return;
        }

        int id = (int) sess.getAttribute("userId");

        UserDAO dao = new UserDAO(DBConnect.getConn());
        User u = dao.getUserById(id);

        req.setAttribute("user", u);
        req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, res);
    }
}
