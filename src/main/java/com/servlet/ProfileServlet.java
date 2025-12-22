package com.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/profile.jsp")
                .forward(req, res);
    }
}
