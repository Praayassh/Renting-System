package com.rentingsystem.controller;

import com.rentingsystem.dao.PropertyDAO;
import com.rentingsystem.model.Property;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@WebServlet("/postProperty")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class PostPropertyServlet extends HttpServlet {
    private PropertyDAO propertyDAO;
    private static final String UPLOAD_DIRECTORY = "/uploads/images";

    @Override
    public void init() throws ServletException {
        super.init();
        propertyDAO = new PropertyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            request.setAttribute("loginMessage", "Please login first to post a property.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String type = request.getParameter("type");
        String priceStr = request.getParameter("price");
        String currency = request.getParameter("currency");
        String location = request.getParameter("location");

        List<String> imageUrlsList = new ArrayList<>();
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIRECTORY;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                String fileName = getFileName(part);
                if (fileName != null && !fileName.isEmpty()) {
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    part.write(uploadFilePath + File.separator + uniqueFileName);
                    imageUrlsList.add(request.getContextPath() + UPLOAD_DIRECTORY + "/" + uniqueFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error uploading images: " + e.getMessage());
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("price", priceStr);
            request.setAttribute("currency", currency);
            request.setAttribute("location", location);
            doGet(request, response);
            return;
        }
        String imageUrls = String.join(",", imageUrlsList);

        if (title == null || title.isEmpty() || type == null || type.isEmpty() || priceStr == null || priceStr.isEmpty()) {
            request.setAttribute("errorMessage", "Title, Type, and Price are required fields.");
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("price", priceStr);
            request.setAttribute("currency", currency);
            request.setAttribute("location", location);
            request.setAttribute("imageUrls", imageUrls);
            doGet(request, response);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid price format.");
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("price", priceStr);
            request.setAttribute("currency", currency);
            request.setAttribute("location", location);
            request.setAttribute("imageUrls", imageUrls);
            doGet(request, response);
            return;
        }

        Property property = new Property(userId, title, description, type, price, currency, location, imageUrls);

        if (propertyDAO.addProperty(property)) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("errorMessage", "Failed to post property. Please try again.");
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("price", priceStr);
            request.setAttribute("currency", currency);
            request.setAttribute("location", location);
            request.setAttribute("imageUrls", imageUrls);
            doGet(request, response);
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String s : contentDisp.split(";")) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
}
