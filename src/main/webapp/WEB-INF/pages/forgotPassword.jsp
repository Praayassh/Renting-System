<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="login-wrapper">
        <div class="login-form-section">
            <h2 class="forgot-password-heading">Forgot Password</h2>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                String successMessage = (String) request.getAttribute("successMessage");
                String currentStep = (String) request.getAttribute("currentStep");
                if (currentStep == null) currentStep = "step1";
            %>

            <% if (currentStep.equals("step1")) { %>
                <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
                    <div class="login-form-group">
                        <label for="emailOrPhone">Enter your Email or Phone</label>
                        <input type="text" id="emailOrPhone" name="emailOrPhone" value="${emailOrPhone}" required>
                    </div>
                    <div class="login-form-group">
                        <button type="submit" name="action" value="findUser">Next</button>
                    </div>
                </form>
            <% } else if (currentStep.equals("step2")) { %>
                <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
                    <div class="login-form-group">
                        <label for="securityQuestion">Security Question:</label>
                        <input type="text" id="securityQuestion" name="securityQuestion" value="${securityQuestion}" readonly>
                    </div>
                    <div class="login-form-group">
                        <label for="securityAnswer">Your Answer</label>
                        <input type="text" id="securityAnswer" name="securityAnswer" required>
                    </div>
                    <div class="login-form-group">
                        <button type="submit" name="action" value="verifyAnswer">Verify</button>
                    </div>
                </form>
            <% } else if (currentStep.equals("step3")) { %>
                <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
                    <div class="login-form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="login-form-group">
                        <label for="confirmNewPassword">Confirm New Password</label>
                        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                    </div>
                    <div class="login-form-group">
                        <button type="submit" name="action" value="resetPassword">Reset Password</button>
                    </div>
                </form>
            <% } %>
            <div class="login-links">
                <a href="${pageContext.request.contextPath}/">Back to Login</a>
            </div>
            <%
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
        </div>
        <div class="login-image-section">
            <img src="${pageContext.request.contextPath}/images/login_image.png" alt="Forgot Password Image">
        </div>
    </div>
</body>
</html>
