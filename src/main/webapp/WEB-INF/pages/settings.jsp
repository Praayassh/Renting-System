<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/settings.css">
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
            <a href="${pageContext.request.contextPath}/settings/changePassword" class="settings-menu-item">
                <i class="material-icons">vpn_key</i>
                <span>Change Password</span>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="settings-menu-item">
                <i class="material-icons">exit_to_app</i>
                <span>Logout</span>
            </a>
        </div>
        <div class="settings-content">
            <p>Select an option from the menu.</p>
        </div>
    </div>
</body>
</html>
