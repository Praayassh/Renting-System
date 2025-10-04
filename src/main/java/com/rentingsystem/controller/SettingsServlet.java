package com.rentingsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            request.setAttribute("loginMessage", "Please login first to access settings.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
    }
}
