<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
    <header class="header">
        <div class="header__left">
            <img class="header__logo" src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
        </div>
        <div class="header__center">
            <input type="text" class="header__search-input" placeholder="Search">
            <button class="header__search-button">
                <i class="material-icons">search</i>
            </button>
            <button class="header__filter-button" title="Filter">
                <i class="material-icons">filter_alt</i>
            </button>
        </div>
        <div class="header__right">
<a href="${pageContext.request.contextPath}/postProperty" class="header__link">
                <i class="material-icons">store</i>
                <span>Post</span>
            </a>
            <a href="${pageContext.request.contextPath}/settings" class="header__link" title="Settings">
                <i class="material-icons">settings</i>
            </a>
        </div>
    </header>
</body>
</html>
