<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Complaint</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
<main class="page">
    <div class="top-row">
        <h1>Hi, <c:out value="${sessionScope.user.studentNumber}"/></h1>
        <a class="btn" href="${pageContext.request.contextPath}/student/dashboard">
            Back to Complaints
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <form method="post">
        <div class="form-grid">
            <div>
                <label for="residence">Residence</label>
                <input id="residence" name="residence" type="text" value="<c:out value='${residence}'/>" required>
            </div>
            <div>
                <label for="roomNumber">Room Number</label>
                <input id="roomNumber" name="roomNumber" type="text" value="<c:out value='${roomNumber}'/>" required>
            </div>

            <div class="full-width radio-grid">
                <label class="radio-option"><input type="radio" name="complaintType" value="Windows" ${complaintType == 'Windows' ? 'checked' : ''}> Windows</label>
                <label class="radio-option"><input type="radio" name="complaintType" value="Plumbing" ${complaintType == 'Plumbing' ? 'checked' : ''}> Plumbing</label>
                <label class="radio-option"><input type="radio" name="complaintType" value="Electrical Problem" ${complaintType == 'Electrical Problem' ? 'checked' : ''}> Electrical Problem</label>
                <label class="radio-option"><input type="radio" name="complaintType" value="Carpentry" ${complaintType == 'Carpentry' ? 'checked' : ''}> Carpentry</label>
                <label class="radio-option"><input type="radio" name="complaintType" value="Other" ${complaintType == 'Other' ? 'checked' : ''}> Other</label>
            </div>

            <div class="full-width">
                <label for="description">Description</label>
                <textarea id="description" name="description" required><c:out value="${description}"/></textarea>
            </div>
        </div>

        <div class="actions">
            <button type="submit">Submit Complaint</button>
        </div>
    </form>
</main>
</body>
</html>
