package com.example.campusmaintanance.servlet;

import com.example.campusmaintanance.dao.ComplaintDao;
import com.example.campusmaintanance.model.Complaint;
import com.example.campusmaintanance.model.LoggedUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {

    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LoggedUser user = (LoggedUser) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            List<Complaint> complaints = complaintDao.findByStudentNumber(user.getStudentNumber());
            request.setAttribute("complaints", complaints);
            request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);

        } catch (SQLException exception) {
            exception.printStackTrace();
            request.setAttribute("error", "Could not load complaints: " + exception.getMessage());
            request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
        }
    }
}
