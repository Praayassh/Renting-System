package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deleteProperty")
public class DeletePropertyServlet extends HttpServlet {
    private PropertyDAO propertyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            propertyDAO.deleteProperty(propertyId);
        } catch (NumberFormatException e) {
            // Handle invalid propertyId
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/settings/postHistory");
    }
}
