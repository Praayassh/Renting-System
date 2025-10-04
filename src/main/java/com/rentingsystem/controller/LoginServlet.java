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

@WebServlet("")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String registrationSuccessMessage = (String) session.getAttribute("registrationSuccessMessage");
            if (registrationSuccessMessage != null) {
                request.setAttribute("registrationSuccessMessage", registrationSuccessMessage);
                session.removeAttribute("registrationSuccessMessage");
            }
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailOrPhone = request.getParameter("emailOrPhone");
        String password = request.getParameter("password");

        request.setAttribute("emailOrPhone", emailOrPhone);

        if (emailOrPhone == null || emailOrPhone.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Please provide both Email/Phone and password.");
            doGet(request, response);
            return;
        }

        User user = userDAO.validateUser(emailOrPhone, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("errorMessage", "Invalid credentials.");
            doGet(request, response);
        }
    }
}