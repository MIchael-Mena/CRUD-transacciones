<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="WEB-INF/pages/comunes/inicioHTML.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/inicioHead.jsp"/>
<title>Editando a ${account.customer.name}</title>
<jsp:include page="WEB-INF/pages/comunes/finHead.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/nav.jsp"/>

<main class="py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-3 ">
                <h1 class="h3">Cuenta a nombre de: ${account.customer.name}</h1>
            </div>
            <div class="col-md-6 col-sm-3">
                <c:choose>
                    <c:when test="${!otherCustomers.isEmpty()}">
                        <div class="float-end">
                            <a href="#" class="btn btn-success"
                               data-bs-toggle="modal" data-bs-target="#modalTransferencia">Realizar Transferencia</a>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <jsp:include page="WEB-INF/pages/partes/tablaRegistroTransaccion.jsp"/>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-sm-3">
                    <div class="float-left">
                        <a href="#" class="btn btn-warning"
                           data-bs-toggle="modal" data-bs-target="#modalConsultaBaja">Dar de baja</a>
                    </div>
                </div>
            </div>
        </div>
</main>


<jsp:include page="WEB-INF/pages/partes/modalConsultaBaja.jsp"/>
<jsp:include page="WEB-INF/pages/partes/modalTransferencia.jsp"/>
<jsp:include page="WEB-INF/pages/partes/modalTransaccionEstado.jsp"/>

<c:choose>
    <c:when test="${mostrarModal}">
        <script src="scripts/modalShow.js"></script>
    </c:when>
</c:choose>

<jsp:include page="WEB-INF/pages/comunes/footer.jsp"/>
<jsp:include page="WEB-INF/pages/comunes/finHTML.jsp"/>