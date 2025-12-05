package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // get list from servlet context
        List<User> users = (List<User>) getServletContext().getAttribute("users");

        boolean found = false;

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                found = true;
                break;
            }
        }

        if (found) {
            //create session
            HttpSession sess = req.getSession();
            sess.setAttribute("username", username);
            //forward to profile page
            res.sendRedirect("profile.jsp");
        }else {
            res.getWriter().println("Invalid username or password.");
        }
    }

}
