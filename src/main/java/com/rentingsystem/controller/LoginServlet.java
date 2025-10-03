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

@WebServlet("")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailOrPhone = request.getParameter("emailOrPhone");
        String password = request.getParameter("password");

        request.setAttribute("emailOrPhone", emailOrPhone);

        if (emailOrPhone == null || emailOrPhone.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Please provide both Email/Phone and password.");
            doGet(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email = ? OR phone = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, emailOrPhone);
            stmt.setString(2, emailOrPhone);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (password.equals(dbPassword)) {
                    request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Invalid credentials.");
                    doGet(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid credentials.");
                doGet(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred.");
            doGet(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}