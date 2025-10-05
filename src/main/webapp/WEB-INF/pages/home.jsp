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

    <div class="container">

        <div id="notification-area" class="notification-area"></div>

        <div class="filter-container">
            <form action="${pageContext.request.contextPath}/home" method="get">
                <select name="location">
                    <option value="">All Locations</option>
                    <option value="Kathmandu" ${"Kathmandu".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Kathmandu</option>
                    <option value="Pokhara" ${"Pokhara".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Pokhara</option>
                    <option value="Lalitpur" ${"Lalitpur".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Lalitpur</option>
                    <option value="Bhaktapur" ${"Bhaktapur".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Bhaktapur</option>
                    <option value="Biratnagar" ${"Biratnagar".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Biratnagar</option>
                    <option value="Birgunj" ${"Birgunj".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Birgunj</option>
                    <option value="Dharan" ${"Dharan".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Dharan</option>
                    <option value="Butwal" ${"Butwal".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Butwal</option>
                    <option value="Hetauda" ${"Hetauda".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Hetauda</option>
                    <option value="Janakpur" ${"Janakpur".equals(request.getAttribute("selectedLocation")) ? "selected" : ""}>Janakpur</option>
                </select>
                <select name="type">
                    <option value="">All Types</option>
                    <option value="flat" ${"flat".equals(request.getAttribute("selectedType")) ? "selected" : ""}>Flat</option>
                    <option value="room" ${"room".equals(request.getAttribute("selectedType")) ? "selected" : ""}>Room</option>
                    <option value="shop" ${"shop".equals(request.getAttribute("selectedType")) ? "selected" : ""}>Shop</option>
                </select>
                <button type="submit">Filter</button>
            </form>
        </div>

        <div class="property-list">
            <%
                List<Property> allProperties = (List<Property>) request.getAttribute("allProperties");
                if (allProperties != null && !allProperties.isEmpty()) {
            %>
                <% for (Property property : allProperties) { %>
                        <div class="property-card">
                            <a href="${pageContext.request.contextPath}/propertyDetails?propertyId=<%= property.getId() %>" class="card-overlay-link"></a>
                            <div class="card-menu-button" onclick="toggleMenu(this)">
                                <i class="material-icons">more_vert</i>
                            </div>
                            <div class="card-menu">
                                <button type="button" onclick="event.stopPropagation(); savePost(<%= property.getId() %>, this)">Save Post</button>
                            </div>
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
                        </div>
                <% } %>
            <%
                } else {
            %>
                <p class="no-posts">No properties found matching your criteria.</p>
            <%
                }
            %>
        </div>
    </div>

    <script>
        function toggleMenu(button) {
            const menu = button.nextElementSibling;
            menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
            event.stopPropagation();
        }

        function showNotification(message, isSuccess) {
            const notificationArea = document.getElementById('notification-area');
            notificationArea.textContent = message;
            notificationArea.className = 'notification-area ' + (isSuccess ? 'success' : 'error');
            notificationArea.style.display = 'block';

            setTimeout(() => {
                notificationArea.style.display = 'none';
            }, 3000); // Hide after 3 seconds
        }

        function savePost(propertyId, buttonElement) {
            fetch('${pageContext.request.contextPath}/savePost', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'propertyId=' + propertyId
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showNotification('Post saved successfully!', true);
                    buttonElement.textContent = 'Saved';
                    buttonElement.disabled = true;
                } else {
                    showNotification(data.message, false);
                }
            })
            .catch(error => {
                showNotification('An error occurred while saving the post.', false);
                console.error('Error:', error);
            });
        }

        window.onclick = function(event) {
            document.querySelectorAll('.card-menu').forEach(function(menu) {
                if (menu.style.display === 'block') {
                    menu.style.display = 'none';
                }
            });
        }
    </script>
</body>
</html>
