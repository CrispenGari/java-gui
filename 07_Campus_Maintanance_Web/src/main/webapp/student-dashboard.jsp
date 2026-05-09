<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student/Staff Complaints</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/styles.css"
    />
  </head>
  <body>
    <main class="page">
      <div class="top-row">
        <h1>Hi, <c:out value="${sessionScope.user.studentNumber}" /></h1>
      </div>

      <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">
          <c:out value="${sessionScope.success}" />
        </div>
        <c:remove var="success" scope="session" />
      </c:if>
      <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}" /></div>
      </c:if>

      <section class="table-panel">
        <div class="table-scroll">
          <table>
            <thead>
              <tr>
                <th>Complaint</th>
                <th>Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty complaints}">
                  <tr>
                    <td colspan="3" class="empty">
                      No complaints submitted yet.
                    </td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="complaint" items="${complaints}">
                    <tr>
                      <td><c:out value="${complaint.summary}" /></td>
                      <td><c:out value="${complaint.createdAt}" /></td>
                      <td><c:out value="${complaint.status}" /></td>
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </section>

      <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/student/complaints/new">
          New Complaint
        </a>
        <form
          class="inline-form"
          method="post"
          action="${pageContext.request.contextPath}/logout"
        >
          <button class="btn-danger" type="submit">Logout</button>
        </form>
      </div>
    </main>
  </body>
</html>
