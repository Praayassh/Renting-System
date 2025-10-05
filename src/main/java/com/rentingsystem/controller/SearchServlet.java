package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import com.rentingsystem.model.Property;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private PropertyDAO propertyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        List<Property> searchResults;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchResults = propertyDAO.searchProperties(searchQuery);
        } else {
            searchResults = propertyDAO.getAllProperties();
        }

        request.setAttribute("allProperties", searchResults);
        request.setAttribute("searchQuery", searchQuery);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
