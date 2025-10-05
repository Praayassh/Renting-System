<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rentingsystem.model.Property" %>
<html>
<head>
    <title>Saved Posts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

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
            <a href="${pageContext.request.contextPath}/settings/postHistory" class="settings-menu-item">
                <i class="material-icons">history</i>
                <span>Post History</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings/savedPosts" class="settings-menu-item active">
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
            <h3>Your Saved Posts</h3>
            <%
                List<Property> savedProperties = (List<Property>) request.getAttribute("savedProperties");
                if (savedProperties != null && !savedProperties.isEmpty()) {
            %>
                <div class="property-list">
                    <% for (Property property : savedProperties) { %>
                        <div class="property-card">
                            <a href="${pageContext.request.contextPath}/propertyDetails?propertyId=<%= property.getId() %>" class="card-overlay-link"></a>
                            <%
                                String imageUrl = "https://via.placeholder.com/300x180?text=No+Image";
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
                                <button type="button" class="delete-button" onclick="event.stopPropagation(); alert('Unsave successful'); unsavePost(<%= property.getId() %>, this)">Unsave</button>
                            </div>
                        </div>
                    <% } %>
                </div>
            <%
                } else {
            %>
                <p class="no-posts">You haven't saved any posts yet.</p>
            <%
                }
            %>
        </div>
    </div>

    <script>
        function unsavePost(propertyId, buttonElement) {
            console.log('unsavePost called for propertyId:', propertyId);
            fetch('${pageContext.request.contextPath}/unsavePost', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'propertyId=' + propertyId
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    console.log('Post unsaved!');
                    const card = buttonElement.closest('.property-card');
                    if (card) {
                        card.remove();
                    }
                } else {
                    console.error('Error unsaving post:', data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    </script>
</body>
</html>
