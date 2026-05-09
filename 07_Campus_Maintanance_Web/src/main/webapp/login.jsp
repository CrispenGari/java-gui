<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Residence Complaints Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
<main class="center-page">
    <form class="login-card" action="${pageContext.request.contextPath}/login" method="post">
        <div class="logo-wrap">
            <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo.png" alt="Campus Maintenance Logo">
        </div>

        <label for="emailOrStudentNumber">Email Address/Student Number</label>
        <input id="emailOrStudentNumber" name="emailOrStudentNumber" type="text"
               placeholder="Email Address/Student Number"
               value="<c:out value='${emailOrStudentNumber}'/>" required>

        <label for="role">Role</label>
        <select id="role" name="role">
            <option value="student/staff" ${selectedRole == 'student/staff' ? 'selected' : ''}>student/staff</option>
            <option value="admin" ${selectedRole == 'admin' ? 'selected' : ''}>admin</option>
        </select>

        <div id="passwordGroup">
            <label for="password">Password</label>
            <input id="password" name="password" type="password" placeholder="Password" required>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <button type="submit">Sign In</button>
    </form>
</main>
<script>
    const role = document.getElementById('role');
    const passwordGroup = document.getElementById('passwordGroup');
    const password = document.getElementById('password');

    function togglePassword() {
        const isAdmin = role.value === 'admin';
        passwordGroup.style.display = isAdmin ? 'block' : 'none';
        password.required = isAdmin;
    }

    role.addEventListener('change', togglePassword);
    togglePassword();
</script>
</body>
</html>
