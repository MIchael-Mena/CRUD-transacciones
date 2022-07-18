<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="modalTransferencia" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Transferencia</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="formTransferencia" action="${pageContext.request.contextPath}/app?accion=transfer&id=${account.id}"
                  method="post" class="was-validated">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-12">

                            <%--Seria mas correcto que el usuario escriba el id de la cuenta a la que va a transferir--%>
                            <label for="idDestiny" class="form-label">Seleccionar una cuenta: </label>
                            <select class="form-select" id="idDestiny" name="idDestiny">
                                <c:forEach items="${otherAccounts}" var="destinyAccount">
                                    <option value="${destinyAccount.id}">${destinyAccount.name}</option>
                                </c:forEach>
                            </select>

                            <div class="pt-3">
                            </div>
                            <label for="amount" class="form-label">Cantidad</label>
                            <input type="text" class="form-control" id="amount" name="amount" required>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-success">Enviar</button>
                </div>
            </form>
        </div>
    </div>
</div>