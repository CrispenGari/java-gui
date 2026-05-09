package com.example.campusmaintanance.servlet;
import com.example.campusmaintanance.dao.ComplaintDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/complaints/status")
public class UpdateStatusServlet extends HttpServlet {
    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String selectedId = request.getParameter("selectedId");
        String status = request.getParameter("status");
        try {
            int complaintId = Integer.parseInt(selectedId);
            complaintDao.updateStatus(complaintId, status);
            request.getSession().setAttribute("success", "Status updated successfully.");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?selectedId=" + complaintId);
        } catch (Exception exception) {
            request.getSession().setAttribute("error", "Could not update status: " + exception.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
}
