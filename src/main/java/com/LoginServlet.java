package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // ✅ Handle GET requests → load login page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    // ✅ Handle POST requests → login check
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO(DBConnect.getConn());
        User u = dao.getUserByUsername(username);

        if (u != null && BCrypt.checkpw(password, u.getPassword())) {

            HttpSession sess = req.getSession();
            sess.setAttribute("userId", u.getId());
            sess.setAttribute("username", u.getUsername());
            sess.setAttribute("email", u.getEmail());

            res.sendRedirect("users.jsp");

        } else {
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }
}
