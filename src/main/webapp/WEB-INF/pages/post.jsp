<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post New Property</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/header.jsp" />

    <div class="container">
        <h2>Post New Property for Rent</h2>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <p class="error-message"><%= errorMessage %></p>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/postProperty" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="type">Property Type</label>
                <select id="type" name="type" required>
                    <option value="">Select Type</option>
                    <option value="flat" ${"flat".equals(request.getAttribute("type")) ? "selected" : ""}>Flat</option>
                    <option value="room" ${"room".equals(request.getAttribute("type")) ? "selected" : ""}>Room</option>
                    <option value="shop" ${"shop".equals(request.getAttribute("type")) ? "selected" : ""}>Shop</option>
                </select>
            </div>

            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${title}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description">${description}</textarea>
            </div>

            <div class="form-group">
                <label for="currency">Currency</label>
                <select id="currency" name="currency">
                    <option value="NPR" ${"NPR".equals(request.getAttribute("currency")) ? "selected" : ""}>NPR - Nepalese Rupee</option>
                    <option value="USD" ${"USD".equals(request.getAttribute("currency")) ? "selected" : ""}>USD - US Dollar</option>
                    <option value="EUR" ${"EUR".equals(request.getAttribute("currency")) ? "selected" : ""}>EUR - Euro</option>
                    <option value="GBP" ${"GBP".equals(request.getAttribute("currency")) ? "selected" : ""}>GBP - British Pound</option>
                    <option value="INR" ${"INR".equals(request.getAttribute("currency")) ? "selected" : ""}>INR - Indian Rupee</option>
                </select>
            </div>

            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${price}" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="location">Location</label>
                <select id="location" name="location" required>
                    <option value="">Select a city</option>
                    <option value="Kathmandu" ${"Kathmandu".equals(request.getAttribute("location")) ? "selected" : ""}>Kathmandu</option>
                    <option value="Pokhara" ${"Pokhara".equals(request.getAttribute("location")) ? "selected" : ""}>Pokhara</option>
                    <option value="Lalitpur" ${"Lalitpur".equals(request.getAttribute("location")) ? "selected" : ""}>Lalitpur</option>
                    <option value="Bhaktapur" ${"Bhaktapur".equals(request.getAttribute("location")) ? "selected" : ""}>Bhaktapur</option>
                    <option value="Biratnagar" ${"Biratnagar".equals(request.getAttribute("location")) ? "selected" : ""}>Biratnagar</option>
                    <option value="Birgunj" ${"Birgunj".equals(request.getAttribute("location")) ? "selected" : ""}>Birgunj</option>
                    <option value="Dharan" ${"Dharan".equals(request.getAttribute("location")) ? "selected" : ""}>Dharan</option>
                    <option value="Butwal" ${"Butwal".equals(request.getAttribute("location")) ? "selected" : ""}>Butwal</option>
                    <option value="Hetauda" ${"Hetauda".equals(request.getAttribute("location")) ? "selected" : ""}>Hetauda</option>
                    <option value="Janakpur" ${"Janakpur".equals(request.getAttribute("location")) ? "selected" : ""}>Janakpur</option>
                </select>
            </div>

            <div class="form-group">
                <label for="imageFiles">Upload Images</label>
                <input type="file" id="imageFiles" name="imageFiles" multiple accept="image/*">
            </div>

            <div class="form-group form-group--submit">
                <button type="submit">Post Property</button>
            </div>
        </form>
    </div>
</body>
</html>
