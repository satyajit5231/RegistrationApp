package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // ✅ Proper casting (VERY IMPORTANT)
        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // ✅ Disable browser cache (prevents back-button security issue)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // ✅ Logged-in check
        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        // ✅ Public (allowed without login)
        boolean publicResource =
                uri.endsWith("/login.jsp") ||
                        uri.endsWith("/register.jsp") ||
                        uri.endsWith("/forgot.jsp") ||

                        uri.endsWith("/login") ||
                        uri.endsWith("/register") ||
                        uri.endsWith("/sendotp") ||
                        uri.endsWith("/verifyotp") ||
                        uri.endsWith("/resetpassword") ||

                        uri.contains("/css/") ||
                        uri.contains("/js/") ||
                        uri.contains("/images/");

        // ✅ Debug (TEMPORARY — remove later if you want)
        System.out.println("🧱 AuthFilter → " + uri);

        if (loggedIn || publicResource) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
