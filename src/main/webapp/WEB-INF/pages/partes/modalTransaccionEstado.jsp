<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-sm">
    Estado de transaccion
</button>--%>
<div class="modal" id="modalTransaccionEstado" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Estado de Transaccion</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <c:choose>
                    <c:when test="${transaccionAprobada}">
                        <div class="alert alert-secondary text-center" role="alert">
                            Realizada
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-danger text-center" role="alert">
                            <p>Rechazada</p>
                            <p>${mensajeDeError}</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
<%--            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>--%>
        </div>
    </div>
</div>


