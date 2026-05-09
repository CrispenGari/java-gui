package com.example.campusmaintanance.filter;
import com.example.campusmaintanance.model.LoggedUser;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.*;

@WebFilter(urlPatterns = {"/student/*", "/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        LoggedUser user = session == null ? null : (LoggedUser) session.getAttribute("user");

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        String path = httpRequest.getRequestURI();
        if (path.contains("/admin/") && !user.isAdmin()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/student/dashboard");
            return;
        }
        if (path.contains("/student/") && user.isAdmin()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashboard");
            return;
        }

        chain.doFilter(request, response);
    }
}
