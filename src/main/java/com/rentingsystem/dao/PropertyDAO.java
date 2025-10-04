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
}
