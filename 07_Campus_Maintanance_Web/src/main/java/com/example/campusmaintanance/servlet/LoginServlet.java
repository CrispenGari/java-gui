package com.example.campusmaintanance.servlet;

import com.example.campusmaintanance.dao.AdminDao;
import com.example.campusmaintanance.model.LoggedUser;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AdminDao adminDao = new AdminDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailOrStudentNumber = request.getParameter("emailOrStudentNumber");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        emailOrStudentNumber = emailOrStudentNumber == null ? "" : emailOrStudentNumber.trim();
        role = role == null ? "student/staff" : role.trim();
        password = password == null ? "" : password;

        if (emailOrStudentNumber.isBlank()) {
            showError(request, response, "Please enter your email address or student number.");
            return;
        }

        if ("admin".equals(role)) {
            handleAdminLogin(request, response, emailOrStudentNumber, password, role);
        } else {
            handleStudentLogin(request, response, emailOrStudentNumber, role);
        }
    }

    private void handleAdminLogin(HttpServletRequest request, HttpServletResponse response, String email, String password, String role)
            throws ServletException, IOException {
        if (!isValidEmail(email)) {
            showError(request, response, "Admin must use a valid @ufh.ac.za email.");
            return;
        }
        if (password.isBlank()) {
            showError(request, response, "Please enter your password.");
            return;
        }
        try {
            if (adminDao.authenticate(email, password)) {
                String displayName = email.toLowerCase().replace("@ufh.ac.za", "");
                LoggedUser user = new LoggedUser(email.toLowerCase(), role, displayName);
                request.getSession(true).setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                showError(request, response, "Failed to authenticate. Invalid credentials.");
            }
        } catch (SQLException exception) {
            showError(request, response, "Database error: " + exception.getMessage());
        }
    }

    private void handleStudentLogin(HttpServletRequest request, HttpServletResponse response, String input, String role)
            throws IOException, ServletException {
        if (!(isValidEmail(input) || isValidStudentNumber(input))) {
            showError(request, response, "Enter a valid UFH email or 9-digit student number.");
            return;
        }

        String studentNumber = input.contains("@") ? input.substring(0, input.indexOf('@')) : input;
        String email = input.contains("@") ? input.toLowerCase() : input + "@ufh.ac.za";
        LoggedUser user = new LoggedUser(email, role, studentNumber);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/student/dashboard");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@ufh\\.ac\\.za$");
    }

    private boolean isValidStudentNumber(String studentNumber) {
        return studentNumber != null && studentNumber.matches("^\\d{9}$");
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("emailOrStudentNumber", request.getParameter("emailOrStudentNumber"));
        request.setAttribute("selectedRole", request.getParameter("role"));
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
