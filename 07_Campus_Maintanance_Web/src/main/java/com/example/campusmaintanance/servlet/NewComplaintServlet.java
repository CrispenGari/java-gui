package com.example.campusmaintanance.servlet;

import com.example.campusmaintanance.dao.ComplaintDao;
import com.example.campusmaintanance.model.Complaint;
import com.example.campusmaintanance.model.LoggedUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/student/complaints/new")
public class NewComplaintServlet extends HttpServlet {
    private static final Set<String> TYPES = Set.of("Windows", "Plumbing", "Electrical Problem", "Carpentry", "Other");
    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/complaint-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoggedUser user = (LoggedUser) request.getSession().getAttribute("user");
        String residence = clean(request.getParameter("residence"));
        String roomNumber = clean(request.getParameter("roomNumber"));
        String complaintType = clean(request.getParameter("complaintType"));
        String description = clean(request.getParameter("description"));

        if (residence.isBlank() || roomNumber.isBlank() || complaintType.isBlank() || description.isBlank()) {
            keepFormValues(request, residence, roomNumber, complaintType, description);
            request.setAttribute("error", "Please complete all fields.");
            request.getRequestDispatcher("/complaint-form.jsp").forward(request, response);
            return;
        }
        if (!TYPES.contains(complaintType)) {
            keepFormValues(request, residence, roomNumber, complaintType, description);
            request.setAttribute("error", "Please select a valid complaint type.");
            request.getRequestDispatcher("/complaint-form.jsp").forward(request, response);
            return;
        }

        try {
            Complaint complaint = new Complaint(user.getStudentNumber(), residence, roomNumber, complaintType, description);
            complaintDao.create(complaint);
            request.getSession().setAttribute("success", "Complaint submitted successfully.");
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
        } catch (SQLException exception) {
            keepFormValues(request, residence, roomNumber, complaintType, description);
            request.setAttribute("error", "Could not submit complaint: " + exception.getMessage());
            request.getRequestDispatcher("/complaint-form.jsp").forward(request, response);
        }
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private void keepFormValues(HttpServletRequest request, String residence, String roomNumber, String complaintType, String description) {
        request.setAttribute("residence", residence);
        request.setAttribute("roomNumber", roomNumber);
        request.setAttribute("complaintType", complaintType);
        request.setAttribute("description", description);
    }
}
