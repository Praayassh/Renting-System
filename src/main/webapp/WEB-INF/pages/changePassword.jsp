<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/settings.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/changePassword.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="settings-container">
        <h2>User Settings</h2>
        <div class="settings-menu">
            <a href="${pageContext.request.contextPath}/settings/profile" class="settings-menu-item">
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
            <a href="${pageContext.request.contextPath}/settings/changePassword" class="settings-menu-item active">
                <i class="material-icons">vpn_key</i>
                <span>Change Password</span>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="settings-menu-item">
                <i class="material-icons">exit_to_app</i>
                <span>Logout</span>
            </a>
        </div>
        <div class="settings-content">
            <h3>Change Your Password</h3>
            <div class="password-form">
                <%
                    String successMessage = (String) request.getAttribute("successMessage");
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (successMessage != null) {
                %>
                        <p class="message success"><%= successMessage %></p>
                <%
                    }
                    if (errorMessage != null) {
                %>
                        <p class="message error"><%= errorMessage %></p>
                <%
                    }
                %>
                <form action="${pageContext.request.contextPath}/settings/changePassword" method="post">
                    <div class="form-group">
                        <label for="currentPassword">Current Password</label>
                        <input type="password" id="currentPassword" name="currentPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmNewPassword">Confirm New Password</label>
                        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                    </div>
                    <div class="form-group form-group--submit">
                        <button type="submit">Change Password</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
