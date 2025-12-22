package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/register"})
public class GuestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession sess = req.getSession(false);

        if (sess != null && sess.getAttribute("userId") != null) {
            res.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        chain.doFilter(request, response);
    }
}
