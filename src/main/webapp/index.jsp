
<jsp:include page="WEB-INF/pages/comunes/inicioHTML.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/inicioHead.jsp"/>
<title>Home</title>
<jsp:include page="WEB-INF/pages/comunes/finHead.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/nav.jsp"/>

<% response.sendRedirect("/CRUD_transacciones/"); %>

<main class="p-5 bg-light rounded-3">
    <div class="container-fluid py-5 col-8">
        <h1 class="display-5 fw-bold">WebApp Java MVC con base de datos MySQL</h1>
        <p class="fs-4">Este es un proyecto CRUD (Create, Read, Update, Delete) con fines educativos.</p>
        <a class="btn btn-warning btn-lg" href="${pageContext.request.contextPath}/app">Ir a la app</a>
    </div>
</main>

<jsp:include page="WEB-INF/pages/comunes/footer.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/finHTML.jsp"/>
