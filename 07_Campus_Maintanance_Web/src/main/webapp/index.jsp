<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
<br/>
<a href="login">Please</a>
</body>
</html>