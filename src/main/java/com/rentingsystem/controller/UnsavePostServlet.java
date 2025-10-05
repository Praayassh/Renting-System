package com.rentingsystem.controller;

import com.rentingsystem.dao.SavedPostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/unsavePost")
public class UnsavePostServlet extends HttpServlet {
    private SavedPostDAO savedPostDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        savedPostDAO = new SavedPostDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().write("{\"success\": false, \"message\": \"User not logged in.\"}");
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));

            boolean unsaved = savedPostDAO.unsavePost(userId, propertyId);
            if (unsaved) {
                response.getWriter().write("{\"success\": true, \"message\": \"Post unsaved successfully.\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to unsave post.\"}");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid property ID.\"}");
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"message\": \"An error occurred: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
