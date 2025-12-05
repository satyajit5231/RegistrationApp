package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = (List<User>) getServletContext().getAttribute("users");

        if (users == null) {
            users = new ArrayList<>();
            getServletContext().setAttribute("users", users);
        }

        String username = request.getParameter("username");
        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        List<String> errors = new ArrayList<>();

        if (username == null || username.trim().isEmpty()) {
            errors.add("Username is required.");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.add("Email is required.");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required.");
        } else if (password.length() < 6) {
            errors.add("Password must be at least 6 characters.");
        }

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                errors.add("Username already taken.");
                break;
            }
        }

        if (!errors.isEmpty()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<h2>Registration Failed</h2>");
            out.println("<ul>");
            for (String err : errors) {
                out.println("<li>" + err + "</li>");
            }
            out.println("</ul>");
            out.println("<a href=\"register.html\">Go back</a>");
            return;
        }

        User newUser = new User(username, email, password);
        users.add(newUser);

        request.setAttribute("username", username);
        request.setAttribute("email", email);

        request.getRequestDispatcher("success.jsp").forward(request, response);
    }
}