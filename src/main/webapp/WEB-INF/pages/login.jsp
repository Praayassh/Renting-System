<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />
    <div class="login-wrapper">
        <div class="login-form-section">
            <h2>Login</h2>
            <form action="${pageContext.request.contextPath}/" method="post">
                <div class="login-form-group">
                    <label for="emailOrPhone">Email or Phone</label>
                    <input type="text" id="emailOrPhone" name="emailOrPhone" value="${emailOrPhone}" required>
                </div>
                <div class="login-form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <%
                    String registrationSuccessMessage = (String) request.getAttribute("registrationSuccessMessage");
                    if (registrationSuccessMessage != null) {
                %>
                    <p class="message success"><%= registrationSuccessMessage %></p>
                <%
                    }
                %>

                <%
                    String loginMessage = (String) request.getAttribute("loginMessage");
                    if (loginMessage != null) {
                %>
                    <p class="message info"><%= loginMessage %></p>
                <%
                    }
                %>

                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                    <p class="message error"><%= errorMessage %></p>
                <%
                    }
                %>

                <div class="login-form-group">
                    <button type="submit">Login</button>
                </div>
            </form>
            <div class="login-links">
                <a href="${pageContext.request.contextPath}/forgotPassword">Forgot Password?</a>
                |
                <a href="${pageContext.request.contextPath}/register">Register</a>
            </div>
        </div>
        <div class="login-image-section">
            <img src="${pageContext.request.contextPath}/images/login_image.png" alt="Login Image">
        </div>
    </div>
</body>
</html>