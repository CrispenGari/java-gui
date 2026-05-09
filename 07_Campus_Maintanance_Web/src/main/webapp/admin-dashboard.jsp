<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
<main class="page">
    <div class="top-row">
        <h1>Admin Dashboard</h1>
    </div>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success"><c:out value="${sessionScope.success}"/></div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error"><c:out value="${sessionScope.error}"/></div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <section class="admin-layout">
        <div class="table-scroll">
            <table>
                <thead>
                <tr>
                    <th>Student #</th>
                    <th>Complaint</th>
                    <th>Status</th>
                    <th>View</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty complaints}">
                        <tr>
                            <td colspan="4" class="empty">No complaints have been submitted yet.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="complaint" items="${complaints}">
                            <tr>
                                <td><c:out value="${complaint.studentNumber}"/></td>
                                <td><c:out value="${complaint.complaintType}"/></td>
                                <td><c:out value="${complaint.status}"/></td>
                                <td>
                                    <a class="row-link" href="${pageContext.request.contextPath}/admin/dashboard?selectedId=${complaint.id}">Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>

        <div class="details-panel">
            <c:choose>
                <c:when test="${not empty selectedComplaint}">
Student #: <c:out value="${selectedComplaint.studentNumber}"/>
Residence: <c:out value="${selectedComplaint.residence}"/>
Room Number: <c:out value="${selectedComplaint.roomNumber}"/>
Complaint Type: <c:out value="${selectedComplaint.complaintType}"/>
Status: <c:out value="${selectedComplaint.status}"/>
Date: <c:out value="${selectedComplaint.createdAt}"/>

Description:
<c:out value="${selectedComplaint.description}"/>
                </c:when>
                <c:otherwise>
Select a complaint row by clicking Details to view the full complaint information.
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <div class="actions">
        <c:if test="${not empty selectedComplaint}">
            <form class="status-form" method="post" action="${pageContext.request.contextPath}/admin/complaints/status">
                <input type="hidden" name="selectedId" value="${selectedComplaint.id}">
                <select name="status" required>
                    <option value="Pending" ${selectedComplaint.status == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="In Progress" ${selectedComplaint.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                    <option value="Resolved" ${selectedComplaint.status == 'Resolved' ? 'selected' : ''}>Resolved</option>
                    <option value="Rejected" ${selectedComplaint.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                </select>
                <button type="submit">Change Status</button>
            </form>
        </c:if>
        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/logout">
            <button class="btn-danger" type="submit">Logout</button>
        </form>
    </div>
</main>
</body>
</html>
