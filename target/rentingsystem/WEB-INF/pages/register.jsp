<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alan+Sans:wght@300..900&display=swap" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="register-container">
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <p class="message error"><%= errorMessage %></p>
        <%
            }
        %>
        <h2>Register</h2>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-row">
                <div class="input-group">
                    <label for="name">Full Name:</label>
                    <input type="text" id="name" name="name" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required>
                </div>
                <div class="input-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" required>
                </div>
            </div>
            <div class="form-row">
                <div class="input-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
                </div>
                <div class="input-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>" required>
                </div>
            </div>
            <div class="form-row">
                <div class="input-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="input-group">
                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
            </div>

            <button class="regbut" type="submit">Register</button>
        </form>
        <p class="login-link">Already have an account? <a href="${pageContext.request.contextPath}/">Login here</a></p>
    </div>
</body>
</html>