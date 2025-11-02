package com.rentingsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Property implements Serializable {
    private int id;
    private int userId;
    private String title;
    private String description;
    private int typeId;
    private String typeName;
    private double price;
    private String currency;
    private int locationId;
    private String locationName;
    private List<String> imageUrls;
    private Timestamp postedDate;
    private String status;

    public Property() {
    }

    public Property(int id, int userId, String title, String description, int typeId, String typeName, double price, String currency, int locationId, String locationName, List<String> imageUrls, Timestamp postedDate, String status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.typeId = typeId;
        this.typeName = typeName;
        this.price = price;
        this.currency = currency;
        this.locationId = locationId;
        this.locationName = locationName;
        this.imageUrls = imageUrls;
        this.postedDate = postedDate;
        this.status = status;
    }

    public Property(int userId, String title, String description, int typeId, double price, String currency, int locationId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.typeId = typeId;
        this.price = price;
        this.currency = currency;
        this.locationId = locationId;
        this.status = "available";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}