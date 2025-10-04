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

@WebServlet("/settings/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            request.setAttribute("loginMessage", "Please login first to change your password.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        // Fetch user to verify current password
        User user = userDAO.getUserById(userId);

        if (user == null) {
            request.setAttribute("errorMessage", "User not found.");
            doGet(request, response);
            return;
        }

        // Verify current password
        if (!user.getPassword().equals(currentPassword)) {
            request.setAttribute("errorMessage", "Incorrect current password.");
            doGet(request, response);
            return;
        }

        // Validate new password
        if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(confirmNewPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match or are empty.");
            doGet(request, response);
            return;
        }
        if (!newPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            request.setAttribute("errorMessage", "New password must be at least 8 characters and include at least one letter and one number.");
            doGet(request, response);
            return;
        }

        // Update password
        if (userDAO.updateUserPassword(userId, newPassword)) {
            request.setAttribute("successMessage", "Password updated successfully!");
            doGet(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to update password. Please try again.");
            doGet(request, response);
        }
    }
}
