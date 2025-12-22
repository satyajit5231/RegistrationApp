package com.servlet;

import com.dao.UserDAO;
import com.util.DBConnect;
import com.util.PasswordUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/change-password.jsp")
                .forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String oldPass = req.getParameter("oldPassword");
        String newPass = req.getParameter("newPassword");

        try {
            UserDAO dao = new UserDAO(DBConnect.getConn());
            String storedHash = dao.getPasswordByUsername(username);

            // ❌ Old password wrong
            if (storedHash == null || !BCrypt.checkpw(oldPass, storedHash)) {
                req.setAttribute("error", "Old password incorrect");
                req.getRequestDispatcher("/WEB-INF/views/change-password.jsp")
                        .forward(req, res);
                return;
            }

            // ❌ Weak password
            if (!PasswordUtil.isStrong(newPass)) {
                req.setAttribute("error", "Password must be strong");
                req.getRequestDispatcher("/WEB-INF/views/change-password.jsp")
                        .forward(req, res);
                return;
            }

            // ✅ Update password
            String newHash = BCrypt.hashpw(newPass, BCrypt.gensalt());
            dao.updatePassword(username, newHash);

            // 🔐 IMPORTANT: kill old session
            session.invalidate();

            // 🔁 Force re-login
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Something went wrong");
            req.getRequestDispatcher("/WEB-INF/views/change-password.jsp")
                    .forward(req, res);
        }
    }
}
