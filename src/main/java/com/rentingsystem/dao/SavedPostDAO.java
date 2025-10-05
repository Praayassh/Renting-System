package com.rentingsystem.dao;

import com.rentingsystem.model.Property;
import com.rentingsystem.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedPostDAO {

    public boolean savePost(int userId, int propertyId) {
        String sql = "INSERT INTO saved_posts (user_id, property_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (!e.getSQLState().equals("23000")) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Property> getSavedPropertiesByUserId(int userId) {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT p.* FROM properties p JOIN saved_posts sp ON p.id = sp.property_id WHERE sp.user_id = ? ORDER BY sp.saved_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Property property = new Property();
                    property.setId(rs.getInt("id"));
                    property.setUserId(rs.getInt("user_id"));
                    property.setTitle(rs.getString("title"));
                    property.setDescription(rs.getString("description"));
                    property.setType(rs.getString("type"));
                    property.setPrice(rs.getDouble("price"));
                    property.setCurrency(rs.getString("currency"));
                    property.setLocation(rs.getString("location"));
                    property.setImageUrls(rs.getString("image_urls"));
                    property.setPostedDate(rs.getTimestamp("posted_date"));
                    property.setStatus(rs.getString("status"));
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public boolean unsavePost(int userId, int propertyId) {
        String sql = "DELETE FROM saved_posts WHERE user_id = ? AND property_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
