<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <div class="registration-container">
        <h2>Create Account</h2>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="registration-form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${name}" required>
            </div>
            <div class="registration-form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="${username}" required>
            </div>
            <div class="registration-form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${email}" required>
            </div>
            <div class="registration-form-group">
                <label for="phone">Phone</label>
                <input type="tel" id="phone" name="phone" value="${phone}">
            </div>
            <div class="registration-form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="registration-form-group">
                <label for="confirm_password">Confirm Password</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <p style="color:red;"><%= errorMessage %></p>
            <%
                }
            %>

            <div class="registration-form-group">
                <button type="submit">Register</button>
            </div>
        </form>
        <div class="registration-links">
            <a href="${pageContext.request.contextPath}/">Already have an account?</a>
        </div>
    </div>
</body>
</html>