<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.rentingsystem.model.User" %>
<html>
<head>
    <title>Your Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="profile-page-container">
        <h2>Your Profile</h2>
        <%
            User user = (User) request.getAttribute("user");
            if (user != null) {
        %>
            <div class="profile-details">
                <p><strong>Name:</strong> <%= user.getName() %></p>
                <p><strong>Username:</strong> <%= user.getUsername() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Phone:</strong> <%= user.getPhone() != null && !user.getPhone().isEmpty() ? user.getPhone() : "N/A" %></p>
            </div>
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit" class="logout-button">Logout</button>
            </form>
        <%
            } else {
        %>
            <p>User profile not found.</p>
        <%
            }
        %>
    </div>
</body>
</html>