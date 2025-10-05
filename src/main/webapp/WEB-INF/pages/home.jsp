<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rentingsystem.model.Property" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="property-list">
        <%
            List<Property> allProperties = (List<Property>) request.getAttribute("allProperties");
            if (allProperties != null && !allProperties.isEmpty()) {
        %>
            <% for (Property property : allProperties) { %>
                <div class="property-card">
                    <%
                        String imageUrl = "https://via.placeholder.com/300x180?text=No+Image"; // Default placeholder
                        if (property.getImageUrls() != null && !property.getImageUrls().isEmpty()) {
                            String[] urls = property.getImageUrls().split(",");
                            if (urls.length > 0) {
                                imageUrl = urls[0].trim();
                            }
                        }
                    %>
                    <img src="<%= imageUrl %>" alt="<%= property.getTitle() %>">
                    <div class="property-card-content">
                        <h4><%= property.getTitle() %></h4>
                        <p><strong>Type:</strong> <%= property.getType() %></p>
                        <p><strong>Location:</strong> <%= property.getLocation() != null && !property.getLocation().isEmpty() ? property.getLocation() : "N/A" %></p>
                        <p class="price"><%= property.getCurrency() %> <%= String.format("%.2f", property.getPrice()) %></p>
                        <p><strong>Status:</strong> <%= property.getStatus() %></p>
                        <p><strong>Posted:</strong> <%= property.getPostedDate() %></p>
                    </div>
                </div>
            <% } %>
        <%
            } else {
        %>
            <p class="no-posts">No properties have been posted yet.</p>
        <%
            }
        %>
    </div>
</body>
</html>
