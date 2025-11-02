package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import com.rentingsystem.model.Property;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private PropertyDAO propertyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        String type = request.getParameter("type");

        List<Property> properties;
        if ((location != null && !location.isEmpty()) || (type != null && !type.isEmpty())) {
            properties = propertyDAO.getFilteredProperties(location, type);
        } else {
            properties = propertyDAO.getAllProperties();
        }


        request.setAttribute("allProperties", properties);
        request.setAttribute("selectedLocation", location);
        request.setAttribute("selectedType", type);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}