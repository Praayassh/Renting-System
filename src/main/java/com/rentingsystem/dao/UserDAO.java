package com.rentingsystem.dao;

import com.rentingsystem.model.User;
import com.rentingsystem.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByUsernameOrEmailOrPhone(String identifier) {
        String sql = "SELECT id, name, username, email, phone, password FROM users WHERE username = ? OR email = ? OR phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            stmt.setString(3, identifier);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (name, username, email, phone, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User validateUser(String identifier, String password) {
        User user = getUserByUsernameOrEmailOrPhone(identifier);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUserById(int userId) {
        String sql = "SELECT id, name, username, email, phone, password FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
