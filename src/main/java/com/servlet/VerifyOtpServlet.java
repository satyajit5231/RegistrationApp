package com.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/verifyotp")
public class VerifyOtpServlet extends HttpServlet {

    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession sess = req.getSession(false);

        String email = sess != null ? (String) sess.getAttribute("email") : null;
        String phone = sess != null ? (String) sess.getAttribute("phone") : null;

        if (sess == null ||
                sess.getAttribute("otp") == null ||
                sess.getAttribute("otpTime") == null ||
                (email == null && phone == null))  {

            res.sendRedirect( req.getContextPath()+"forgot.jsp");
            return;
        }

        int enteredOtp;
        try {
            enteredOtp = Integer.parseInt(req.getParameter("otp"));
        } catch (Exception e) {
            req.setAttribute("error", "Invalid OTP");
            req.getRequestDispatcher("/WEB-INF/views/verifyotp.jsp")
                    .forward(req, res);
            return;
        }

        int correctOtp = (int) sess.getAttribute("otp");
        long otpTime = (long) sess.getAttribute("otpTime");

        if (System.currentTimeMillis() - otpTime > OTP_EXPIRY_TIME) {
            sess.invalidate();
            res.sendRedirect(req.getContextPath()+"/forgot.jsp");
            return;
        }

        if (enteredOtp == correctOtp) {
            sess.removeAttribute("otp");
            sess.removeAttribute("otpTime");

            req.getRequestDispatcher("/WEB-INF/views/resetpassword.jsp")
                    .forward(req, res);
        } else {
            req.setAttribute("error", "Incorrect OTP");
            req.getRequestDispatcher("/WEB-INF/views/verifyotp.jsp")
                    .forward(req, res);
        }
    }
}
