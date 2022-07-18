<%--
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World as</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet pepe</a>
</body>
</html>
--%>

<jsp:include page="WEB-INF/pages/comunes/inicioHTML.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/inicioHead.jsp"/>
<title>Home</title>
<jsp:include page="WEB-INF/pages/comunes/finHead.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/nav.jsp"/>

<div class="p-5 mb-4 bg-light rounded-3">
    <div class="container-fluid py-5 col-8">
        <h1 class="display-5 fw-bold">WebApp Java MVC con base de datos MySQL</h1>
        <p class="fs-4">Este es un proyecto CRUD (Create, Read, Update, Delete) con fines educativos para el programa
            "Codo a Codo"</p>
        <a class="btn btn-warning btn-lg" href="${pageContext.request.contextPath}/app">Ir a la app</a>
    </div>
</div>

<jsp:include page="WEB-INF/pages/comunes/footer.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/finHTML.jsp"/>
