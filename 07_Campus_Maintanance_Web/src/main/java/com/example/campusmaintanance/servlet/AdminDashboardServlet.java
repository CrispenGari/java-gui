package com.example.campusmaintanance.servlet;

import com.example.campusmaintanance.dao.ComplaintDao;
import com.example.campusmaintanance.model.Complaint;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("complaints", complaintDao.findAll());
            String selectedId = request.getParameter("selectedId");
            if (selectedId != null && !selectedId.isBlank()) {
                Complaint selectedComplaint = complaintDao.findById(Integer.parseInt(selectedId));
                request.setAttribute("selectedComplaint", selectedComplaint);
            }
            request.getRequestDispatcher("/admin-dashboard.jsp").forward(request, response);
        } catch (Exception exception) {
            request.setAttribute("error", "Could not load admin table: " + exception.getMessage());
            request.getRequestDispatcher("/admin-dashboard.jsp").forward(request, response);
        }
    }
}
