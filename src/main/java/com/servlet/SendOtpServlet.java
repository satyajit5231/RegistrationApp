package com.servlet;

import com.util.DBConnect;
import com.util.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/sendotp")
public class SendOtpServlet extends HttpServlet {

    private static final SecureRandom random = new SecureRandom();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // 1️⃣ Validate input
        if ((email == null || email.isBlank()) &&
                (phone == null || phone.isBlank())) {

            req.setAttribute("error", "Enter email or phone number");
            req.getRequestDispatcher("/forgot.jsp").forward(req, res);
            return;
        }

        try (Connection conn = DBConnect.getConn()) {

            // 2️⃣ Check user exists
            String sql;
            PreparedStatement ps;

            if (email != null && !email.isBlank()) {
                sql = "SELECT 1 FROM users WHERE email = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, email);
            } else {
                // ✅ FORMAT PHONE TO E.164
                if (!phone.startsWith("+")) {
                    phone = "+91" + phone;   // India country code
                }

                sql = "SELECT 1 FROM users WHERE phone = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, phone);
            }

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                res.sendRedirect(req.getContextPath() + "/forgot.jsp");
                return;
            }

            // 3️⃣ Generate secure OTP
            int otp = 100000 + random.nextInt(900000);

            HttpSession session = req.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("otpTime", System.currentTimeMillis());

            // 4️⃣ Send OTP
            if (email != null && !email.isBlank()) {
                session.setAttribute("email", email);

                EmailSender.sendEmail(
                        email,
                        "Account Recovery OTP",
                        "Your OTP is: " + otp +
                                "\nValid for 5 minutes.\nDo not share this OTP."
                );
            }



            // 5️⃣ Forward to OTP verification
            req.getRequestDispatcher("/WEB-INF/views/verifyotp.jsp")
                    .forward(req, res);

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect(req.getContextPath() + "/forgot.jsp");
        }
    }
}
