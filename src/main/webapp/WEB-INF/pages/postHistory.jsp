<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rentingsystem.model.Property" %>
<html>
<head>
    <title>Post History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/settings.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/postHistory.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="settings-container">
        <h2>User Settings</h2>
        <div class="settings-menu">
            <a href="${pageContext.request.contextPath}/settings/profile" class="settings-menu-item">
                <i class="material-icons">person</i>
                <span>Profile</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/postHistory" class="settings-menu-item active">
                <i class="material-icons">history</i>
                <span>Post History</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/savedPosts" class="settings-menu-item">
                <i class="material-icons">bookmark</i>
                <span>Saved Posts</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/changePassword" class="settings-menu-item">
                <i class="material-icons">vpn_key</i>
                <span>Change Password</span>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="settings-menu-item">
                <i class="material-icons">exit_to_app</i>
                <span>Logout</span>
            </a>
        </div>
        <div class="settings-content">
            <h3>Your Post History</h3>
            <%
                List<Property> postedProperties = (List<Property>) request.getAttribute("postedProperties");
                if (postedProperties != null && !postedProperties.isEmpty()) {
            %>
                <div class="property-list">
                    <% for (Property property : postedProperties) { %>
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
                            <div class="property-card-actions">
                                <%
                                    if ("available".equalsIgnoreCase(property.getStatus())) {
                                %>
                                    <form action="${pageContext.request.contextPath}/updateStatus" method="post" style="display: inline-block;">
                                        <input type="hidden" name="propertyId" value="<%= property.getId() %>">
                                        <input type="hidden" name="status" value="unavailable">
                                        <button type="submit" class="unavailable-button">Mark as Unavailable</button>
                                    </form>
                                <%
                                    } else {
                                %>
                                    <form action="${pageContext.request.contextPath}/updateStatus" method="post" style="display: inline-block;">
                                        <input type="hidden" name="propertyId" value="<%= property.getId() %>">
                                        <input type="hidden" name="status" value="available">
                                        <button type="submit" class="available-button">Mark as Available</button>
                                    </form>
                                <%
                                    }
                                %>
                                <form action="${pageContext.request.contextPath}/deleteProperty" method="post" onsubmit="return confirm('Are you sure you want to delete this post?');" style="display: inline-block;">
                                    <input type="hidden" name="propertyId" value="<%= property.getId() %>">
                                    <button type="submit" class="delete-button">Delete</button>
                                </form>
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
    </div>
</body>
</html>
