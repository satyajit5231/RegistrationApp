package com.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession sess = req.getSession(false);
        if (sess != null) {
            sess.invalidate();
        }

        res.sendRedirect(req.getContextPath() + "/login.jsp");

    }
}
