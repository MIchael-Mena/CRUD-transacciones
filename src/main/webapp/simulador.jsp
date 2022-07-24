<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="WEB-INF/pages/comunes/inicioHTML.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/inicioHead.jsp"/>
<title>Simulador</title>
<jsp:include page="WEB-INF/pages/comunes/finHead.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/nav.jsp"/>

<div class="p-5 mb-4 bg-light rounded-3">
    <div class="container-fluid py-5 col-8">
        <div class="row">
            <a href="#" class="btn btn-success"
               data-bs-toggle="modal" data-bs-target="#modalCrearCuenta">Crear cuenta</a>
        </div>
        <div class="row ">
        <c:choose>
            <c:when test="${listCustomers.isEmpty()}">
                <div class="col-12">
                    <p class="display-5 text-danger">Cree una cuenta para utilizar el cajero</p>
                </div>
            </c:when>
            <c:otherwise>
                    <jsp:include page="WEB-INF/pages/partes/cardCajero.jsp"/>
            </c:otherwise>
        </c:choose>
        </div>
        <div class="row g-4 mt-3 row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 row-cols-xxl-5 justify-content-center"
             data-masonry='{"percentPosition": true }'>
            <jsp:include page="WEB-INF/pages/partes/cardCuentas.jsp"/>
        </div>
    </div>
</div>

<jsp:include page="WEB-INF/pages/partes/modalCrearCuenta.jsp"/>
<jsp:include page="WEB-INF/pages/partes/modalTransaccionEstado.jsp"/>

<c:choose>
    <c:when test="${mostrarModal}">
        <script src="scripts/modalShow.js"></script>
    </c:when>
</c:choose>

<jsp:include page="WEB-INF/pages/comunes/footer.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/finHTML.jsp"/>