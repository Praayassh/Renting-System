package com.rentingsystem.controller;

import com.rentingsystem.dao.SavedPostDAO;
import com.rentingsystem.model.Property;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/settings/savedPosts")
public class SavedPostsServlet extends HttpServlet {
    private SavedPostDAO savedPostDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        savedPostDAO = new SavedPostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            request.setAttribute("loginMessage", "Please login first to view your saved posts.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<Property> savedProperties = savedPostDAO.getSavedPropertiesByUserId(userId);

        request.setAttribute("savedProperties", savedProperties);
        request.getRequestDispatcher("/WEB-INF/pages/savedPosts.jsp").forward(request, response);
    }
}
