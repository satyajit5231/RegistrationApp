package com;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        // Block if session invalid
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        UserDAO dao = new UserDAO(DBConnect.getConn());
        String storedHash = dao.getPasswordById(userId);

        // 1. Check old password
        if (!BCrypt.checkpw(oldPassword, storedHash)) {
            res.getWriter().println("❌ Old password incorrect!");
            return;
        }

        // 2. Validate length
        if (newPassword.length() < 6) {
            res.getWriter().println("❌ New password must be at least 6 characters!");
            return;
        }

        // 3. Check confirm match
        if (!newPassword.equals(confirmPassword)) {
            res.getWriter().println("❌ New password & confirm password do not match!");
            return;
        }

        // 4. Encrypt new password
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // 5. Update in DB
        boolean updated = dao.updatePassword(userId, hashed);

        if (updated) {
            session.invalidate(); // logout user
            res.getWriter().println("✔ Password updated! Please login again.");

        } else {
            res.getWriter().println("❌ Failed to update password. Try again.");
        }
    }
}
