<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.rentingsystem.model.User" %>
<html>
<head>
    <title>Profile Settings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/settings.css">
    <style>
        .profile-details {
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            border: 1px solid #eee;
        }
        .profile-details p {
            margin-bottom: 10px;
            font-size: 1.1em;
            color: #333;
        }
        .profile-details p strong {
            display: inline-block;
            width: 120px;
            color: #555;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="settings-container">
        <h2>User Settings</h2>
        <div class="settings-menu">
            <a href="${pageContext.request.contextPath}/settings/profile" class="settings-menu-item active">
                <i class="material-icons">person</i>
                <span>Profile</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/postHistory" class="settings-menu-item">
                <i class="material-icons">history</i>
                <span>Post History</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/savedPosts" class="settings-menu-item">
                <i class="material-icons">bookmark</i>
                <span>Saved Posts</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/changePassword" class="settings-menu-item">
                <i class="material-icons">vpn_key</i>
                <span>Change Password</span>
            </a>
        </div>
        <div class="settings-content">
            <h3>Your Profile</h3>
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
            <%
                } else {
            %>
                <p>User profile not found.</p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
