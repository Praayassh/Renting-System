package com.rentingsystem.controller;

import com.rentingsystem.dao.UserDAO;
import com.rentingsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentStep", "step1");
        request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(); // Get or create session

        if (action == null || action.isEmpty()) {
            request.setAttribute("errorMessage", "Invalid action.");
            request.setAttribute("currentStep", "step1");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "findUser":
                handleFindUser(request, response, session);
                break;
            case "verifyAnswer":
                handleVerifyAnswer(request, response, session);
                break;
            case "resetPassword":
                handleResetPassword(request, response, session);
                break;
            default:
                request.setAttribute("errorMessage", "Unknown action.");
                request.setAttribute("currentStep", "step1");
                request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
                break;
        }
    }

    private void handleFindUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String emailOrPhone = request.getParameter("emailOrPhone");

        if (emailOrPhone == null || emailOrPhone.isEmpty()) {
            request.setAttribute("errorMessage", "Please enter your email or phone number.");
            request.setAttribute("currentStep", "step1");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUserByUsernameOrEmailOrPhone(emailOrPhone);

        if (user != null) {
            String securityQuestionValue = user.getSecurityQuestion();
            String securityQuestionText = getSecurityQuestionText(securityQuestionValue);

            session.setAttribute("resetUserId", user.getId());
            session.setAttribute("securityQuestion", securityQuestionValue);
            request.setAttribute("securityQuestion", securityQuestionText);
            request.setAttribute("currentStep", "step2");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "User not found with the provided email or phone.");
            request.setAttribute("emailOrPhone", emailOrPhone);
            request.setAttribute("currentStep", "step1");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
        }
    }

    private String getSecurityQuestionText(String value) {
        switch (value) {
            case "pet":
                return "What was the name of your first pet?";
            case "mother":
                return "What is your mother's maiden name?";
            case "city":
                return "What city were you born in?";
            default:
                return "";
        }
    }

    private void handleVerifyAnswer(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Integer resetUserId = (Integer) session.getAttribute("resetUserId");
        String storedSecurityQuestion = (String) session.getAttribute("securityQuestion");
        String securityAnswer = request.getParameter("securityAnswer");

        if (resetUserId == null || storedSecurityQuestion == null) {
            request.setAttribute("errorMessage", "Session expired or invalid request. Please start over.");
            request.setAttribute("currentStep", "step1");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        if (securityAnswer == null || securityAnswer.isEmpty()) {
            request.setAttribute("errorMessage", "Please provide an answer to the security question.");
            request.setAttribute("securityQuestion", getSecurityQuestionText(storedSecurityQuestion));
            request.setAttribute("currentStep", "step2");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUserById(resetUserId);
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            session.setAttribute("canResetPassword", true);
            request.setAttribute("currentStep", "step3");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Incorrect security answer.");
            request.setAttribute("securityQuestion", getSecurityQuestionText(storedSecurityQuestion));
            request.setAttribute("currentStep", "step2");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
        }
    }

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Integer resetUserId = (Integer) session.getAttribute("resetUserId");
        Boolean canResetPassword = (Boolean) session.getAttribute("canResetPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if (resetUserId == null || canResetPassword == null || !canResetPassword) {
            request.setAttribute("errorMessage", "Unauthorized password reset attempt. Please start over.");
            request.setAttribute("currentStep", "step1");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(confirmNewPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match or are empty.");
            request.setAttribute("currentStep", "step3");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            request.setAttribute("errorMessage", "Password: 8+ characters, with letters and numbers.");
            request.setAttribute("currentStep", "step3");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        if (userDAO.updateUserPassword(resetUserId, newPassword)) {
            session.removeAttribute("resetUserId");
            session.removeAttribute("securityQuestion");
            session.removeAttribute("canResetPassword");
            session.setAttribute("registrationSuccessMessage", "Password reset successfully!");
            response.sendRedirect(request.getContextPath() + "/"); // Redirect to login page
        } else {
            request.setAttribute("errorMessage", "Failed to reset password. Please try again.");
            request.setAttribute("currentStep", "step3");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
        }
    }
}
