<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <div class="login-container">
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
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <p style="color:red;"><%= errorMessage %></p>
            <%
                }
            %>

            <div class="login-form-group">
                <button type="submit">Login</button>
            </div>
        </form>
        <div class="login-links">
            <a href="${pageContext.request.contextPath}/forgot-password">Forgot Password?</a>
            |
            <a href="${pageContext.request.contextPath}/register">Register</a>
        </div>
    </div>
</body>
</html>