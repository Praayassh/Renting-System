package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import com.rentingsystem.dao.UserDAO;
import com.rentingsystem.model.Property;
import com.rentingsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/propertyDetails")
public class PropertyDetailsServlet extends HttpServlet {
    private PropertyDAO propertyDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            Property property = propertyDAO.getPropertyById(propertyId);

            if (property != null) {
                User owner = userDAO.getUserById(property.getUserId());
                request.setAttribute("property", property);
                request.setAttribute("owner", owner);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/pages/propertyDetails.jsp").forward(request, response);
    }
}