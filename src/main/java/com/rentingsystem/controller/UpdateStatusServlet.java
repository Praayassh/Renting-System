package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {
    private PropertyDAO propertyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            out.print("{\"success\": false, \"message\": \"Unauthorized\"}");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        String status = request.getParameter("status");

        if (propertyDAO.updatePropertyStatus(propertyId, status)) {
            out.print("{\"success\": true, \"message\": \"Status updated successfully\"}");
        } else {
            out.print("{\"success\": false, \"message\": \"Failed to update status\"}");
        }
    }
}
