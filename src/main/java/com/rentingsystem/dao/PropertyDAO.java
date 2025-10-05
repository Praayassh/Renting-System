package com.rentingsystem.dao;

import com.rentingsystem.model.Property;
import com.rentingsystem.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public boolean addProperty(Property property) {
        String sql = "INSERT INTO properties (user_id, title, description, type, price, currency, location, image_urls) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, property.getUserId());
            stmt.setString(2, property.getTitle());
            stmt.setString(3, property.getDescription());
            stmt.setString(4, property.getType());
            stmt.setDouble(5, property.getPrice());
            stmt.setString(6, property.getCurrency());
            stmt.setString(7, property.getLocation());
            stmt.setString(8, property.getImageUrls());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        property.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Property> getPropertiesByUserId(int userId) {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT id, user_id, title, description, type, price, currency, location, image_urls, posted_date, status FROM properties WHERE user_id = ? ORDER BY posted_date DESC";
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

    public List<Property> getAllProperties() {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT id, user_id, title, description, type, price, currency, location, image_urls, posted_date, status FROM properties ORDER BY posted_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public boolean deleteProperty(int propertyId) {
        String sql = "DELETE FROM properties WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propertyId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePropertyStatus(int propertyId, String status) {
        String sql = "UPDATE properties SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, propertyId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
