<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rentingsystem.model.Property" %>
<html>
<head>
    <title>My Posts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/postHistory.css">
</head>
<body>
<jsp:include page="/WEB-INF/pages/header.jsp" />

<div class="post-history-container">
    <h2>My Posted Properties</h2>
    <%
        List<Property> postedProperties = (List<Property>) request.getAttribute("postedProperties");
        if (postedProperties != null && !postedProperties.isEmpty()) {
    %>
    <div class="property-list">
        <% for (Property property : postedProperties) {
            String status = property.getStatus() != null ? property.getStatus().toLowerCase() : "available";
        %>
        <div class="property-card">
            <a href="${pageContext.request.contextPath}/propertyDetails?propertyId=<%= property.getId() %>" class="card-overlay-link"></a>
            <%
                String imageUrl = "https://via.placeholder.com/280x200?text=No+Image";
                if (property.getImageUrls() != null && !property.getImageUrls().isEmpty()) {
                    imageUrl = property.getImageUrls().get(0);
                }
            %>
            <img src="<%= imageUrl %>" alt="<%= property.getTitle() %>">
            <div class="property-card-content">
                <h4><%= property.getTitle() %></h4>
                <p><strong>Type:</strong> <%= property.getTypeName() %></p>
                <p><strong>Location:</strong> <%= property.getLocationName() != null && !property.getLocationName().isEmpty() ? property.getLocationName() : "N/A" %></p>
                <p class="price"><%= property.getCurrency() %> <%= String.format("%.2f", property.getPrice()) %></p>
                <p><strong>Status:</strong>
                    <span id="status-<%= property.getId() %>">
                                        <%= status.substring(0, 1).toUpperCase() + status.substring(1) %>
                                    </span>
                </p>
                <p><strong>Posted:</strong> <%= property.getPostedDate() %></p>
            </div>
            <div class="property-card-actions">
                <button type="button" class="edit-property-button" onclick="event.stopPropagation(); window.location.href='${pageContext.request.contextPath}/editProperty?propertyId=<%= property.getId() %>'">Edit Property</button>
            </div>
        </div>
        <% } %>
    </div>
    <%
    } else {
    %>
    <p class="no-posts">You haven't posted any properties yet.</p>
    <%
        }
    %>
</div>
<%-- Remove JavaScript functions for updateStatus and deletePost if they are not used elsewhere --%>
<script>
    // If updateStatus and deletePost are not needed anymore on this page, you can remove them.
    // If they are used for other functionalities (e.g., admin panel), keep them in a shared JS file.
    // For this specific page, with only an edit button, these functions are no longer directly invoked.
</script>
</body>
</html>