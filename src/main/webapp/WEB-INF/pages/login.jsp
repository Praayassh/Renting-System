<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alan+Sans:wght@300..900&display=swap" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="login-container">
        <div class="login-form-section">
            <h2>Login</h2>
            <form action="${pageContext.request.contextPath}/" method="post">
                <div class="input-group">
                    <label for="identifier">Username, Email, or Phone:</label>
                    <input type="text" id="identifier" name="identifier" required>
                </div>
                <div class="input-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    String registrationSuccessMessage = (String) session.getAttribute("registrationSuccessMessage");

                    if (registrationSuccessMessage != null) {
                %>
                <p class="message success"><%= registrationSuccessMessage %></p>
                <%
                    session.removeAttribute("registrationSuccessMessage");
                } else if (errorMessage != null && !errorMessage.isEmpty()) {
                %>
                <p class="message error"><%= errorMessage %></p>
                <%
                    }
                %>
                <button type="submit">Login</button>
            </form>
            <p class="register-link">Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>

        </div>
    </div>
</body>
</html>