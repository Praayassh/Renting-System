package com.rentingsystem.controller;

import com.rentingsystem.dao.UserDAO;
import com.rentingsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String securityQuestion = request.getParameter("securityQuestion");
        String securityAnswer = request.getParameter("securityAnswer");

        request.setAttribute("name", name);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("securityQuestion", securityQuestion);
        request.setAttribute("securityAnswer", securityAnswer);

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty() || securityQuestion == null || securityQuestion.isEmpty() || securityAnswer == null || securityAnswer.isEmpty()) {
            request.setAttribute("errorMessage", "Please fill out all required fields.");
            doGet(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            doGet(request, response);
            return;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            request.setAttribute("errorMessage", "Invalid email format.");
            doGet(request, response);
            return;
        }
        if (phone != null && !phone.isEmpty() && !phone.matches("^\\d{10}$")) {
            request.setAttribute("errorMessage", "Phone number must be 10 digits.");
            doGet(request, response);
            return;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            request.setAttribute("errorMessage", "Password: 8+ characters, with letters and numbers.");
            doGet(request, response);
            return;
        }

        // Check if user already exists with specific messages
        if (userDAO.getUserByUsernameOrEmailOrPhone(username) != null) {
            request.setAttribute("errorMessage", "Username already exists.");
            doGet(request, response);
            return;
        }
        if (userDAO.getUserByUsernameOrEmailOrPhone(email) != null) {
            request.setAttribute("errorMessage", "Email already exists.");
            doGet(request, response);
            return;
        }
        if (phone != null && !phone.isEmpty() && userDAO.getUserByUsernameOrEmailOrPhone(phone) != null) {
            request.setAttribute("errorMessage", "Phone number already exists.");
            doGet(request, response);
            return;
        }

        User newUser = new User(name, username, email, phone, password, securityQuestion, securityAnswer);
        if (userDAO.addUser(newUser)) {
            request.getSession().setAttribute("registrationSuccessMessage", "Registration successful! Please log in.");
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            doGet(request, response);
        }
    }
}