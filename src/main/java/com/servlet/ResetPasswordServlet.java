package com.servlet;

import com.util.DBConnect;
import com.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/reset")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("email") == null) {
            res.sendRedirect(req.getContextPath() + "/forgot.jsp");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/resetpassword.jsp")
                .forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession sess = req.getSession(false);
        if (sess == null) {
            // ✅ public login page
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String email = (String) sess.getAttribute("email");
        String phone=(String)sess.getAttribute("phone");
        if (email == null) {
            // ✅ public forgot page
            res.sendRedirect( req.getContextPath()+"/forgot.jsp");
            return;
        }

        String newpass = req.getParameter("newpass");
        String cpass = req.getParameter("cpass");

        // 🔐 confirm password check
        if (newpass == null || !newpass.equals(cpass)) {
            req.setAttribute("error", "Passwords do not match");
            req.getRequestDispatcher(
                    "/WEB-INF/views/resetpassword.jsp"
            ).forward(req, res);
            return;
        }

        // 🔐 strong password check
        if (!PasswordUtil.isStrong(newpass)) {
            req.setAttribute("error", "Weak password");
            req.getRequestDispatcher(
                    "/WEB-INF/views/resetpassword.jsp"
            ).forward(req, res);
            return;
        }

        String hashed = BCrypt.hashpw(newpass, BCrypt.gensalt());

        String sql = "UPDATE users SET password=? WHERE email=?";

        try (
                Connection conn = DBConnect.getConn();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, hashed);
            ps.setString(2, email);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                sess.invalidate(); // 🔐 prevent reuse

                // ✅ redirect to public login page
                res.sendRedirect(req.getContextPath()+"/login.jsp?reset=succcess");
            } else {
                req.setAttribute("error", "Password update failed");
                req.getRequestDispatcher(
                        "/WEB-INF/views/resetpassword.jsp"
                ).forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Something went wrong");
            req.getRequestDispatcher(
                    "/WEB-INF/views/resetpassword.jsp"
            ).forward(req, res);
        }
    }
}
