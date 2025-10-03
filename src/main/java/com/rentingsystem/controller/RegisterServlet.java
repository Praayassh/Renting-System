package com.rentingsystem.controller;

import com.rentingsystem.utils.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        request.setAttribute("name", name);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()) {
            request.setAttribute("errorMessage", "Please fill out all required fields.");
            doGet(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            doGet(request, response);
            return;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            request.setAttribute("errorMessage", "Invalid email format.");
            doGet(request, response);
            return;
        }
        if (phone != null && !phone.isEmpty() && !phone.matches("^\\d{10}$")) {
            request.setAttribute("errorMessage", "Phone number must be 10 digits.");
            doGet(request, response);
            return;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters and include at least one letter and one number.");
            doGet(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement checkUserStmt = null;
        PreparedStatement insertUserStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String checkUserSql = "SELECT * FROM users WHERE username = ? OR email = ? OR phone = ?";
            checkUserStmt = conn.prepareStatement(checkUserSql);
            checkUserStmt.setString(1, username);
            checkUserStmt.setString(2, email);
            checkUserStmt.setString(3, phone);
            rs = checkUserStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("errorMessage", "Username, email, or phone number already exists.");
                doGet(request, response);
            } else {
                String insertUserSql = "INSERT INTO users (name, username, email, phone, password) VALUES (?, ?, ?, ?, ?)";
                insertUserStmt = conn.prepareStatement(insertUserSql);
                insertUserStmt.setString(1, name);
                insertUserStmt.setString(2, username);
                insertUserStmt.setString(3, email);
                insertUserStmt.setString(4, phone);
                insertUserStmt.setString(5, password);

                int rowsAffected = insertUserStmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect(request.getContextPath() + "/registration-success");
                } else {
                    request.setAttribute("errorMessage", "Registration failed. Please try again.");
                    doGet(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred.");
            doGet(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkUserStmt != null) checkUserStmt.close();
                if (insertUserStmt != null) insertUserStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}