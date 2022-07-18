<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="modalCrearCuenta" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Crear cuenta</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form id="formTransferencia" action="${pageContext.request.contextPath}/app?accion=create"
            method="post" class="was-validated">
        <div class="modal-body">
          <div class="row">
            <div class="col-12">
              <label for="name" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="name" name="name" required>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
          <button type="submit" class="btn btn-success">Crear</button>
        </div>
      </form>
    </div>
  </div>
</div>