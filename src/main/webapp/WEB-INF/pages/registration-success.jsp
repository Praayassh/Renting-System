<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Successful</title>
    <style>
        p {
            color: green;
            font-size: 45px;
            font-weight: bold;
        }
        a {
            cursor: pointer;
            text-decoration: none;
            color: royalblue;
            font-size: 20px;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <p>Registration Successful!</p>
    <p><a href="${pageContext.request.contextPath}/">Click here to login</a></p>
</body>
</html>
