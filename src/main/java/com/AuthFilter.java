package com;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        // PUBLIC PATHS
        boolean isPublic =
                uri.endsWith("login") ||
                        uri.endsWith("login.jsp") ||
                        uri.endsWith("register") ||
                        uri.endsWith("register.jsp") ||
                        uri.endsWith("success.jsp") ||
                        uri.contains("/css") ||
                        uri.contains("/js") ||
                        uri.contains("/images");

        // BLOCK direct JSP access EXCEPT public JSPs
        if (uri.endsWith(".jsp")) {

            boolean allowedJsp =
                    uri.endsWith("login.jsp") ||
                            uri.endsWith("register.jsp") ||
                            uri.endsWith("success.jsp")||
                            uri.endsWith("change-password.jsp");



            if (!allowedJsp) {
                res.sendRedirect("login");
                return;
            }
        }

        // BLOCK private pages for guests
        if (!loggedIn && !isPublic) {
            res.sendRedirect("login");
            return;
        }

        chain.doFilter(request, response);
    }
}
