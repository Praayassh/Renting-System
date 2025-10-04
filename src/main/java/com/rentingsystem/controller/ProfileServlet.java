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

@WebServlet("/settings/profile")
public class ProfileServlet extends HttpServlet {
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
            request.setAttribute("loginMessage", "Please login first to view your profile.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        int userId = (int) session.getAttribute("userId");
        User user = userDAO.getUserById(userId);

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "User profile not found.");
            request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
        }
    }
}
