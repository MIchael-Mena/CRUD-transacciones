<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col">
    <div class="card h-100">
        <div class="card-body p-4">
            <h1 class="card-title text-center">Cajero</h1>
            <form id="formTransaccion" method="post" class="was-validated border p-4 rounded-3 bg-light "
                  accept-charset="UTF-8">
                <div class="row justify-content-center">
                    <div class="col-sm-6 mb-3">

                        <label for="id" class="form-label">Seleccionar una cuenta: </label>
                        <select class="form-select" id="id" name="id" >
                            <%--                            <option selected>Seleccionar una cuenta:</option>--%>
                            <c:forEach items="${listCustomers}" var="customer">
                                <option value="${customer.id}">${customer.name}</option>
                                <%--                               <option value="2">Two</option>--%>
                            </c:forEach>
                        </select>

                        <label for="amount" class="form-label">Cantidad: </label>
                        <input type="text" class="form-control" id="amount" name="amount" required>
                    </div>
                </div>
                <div class="row justify-content-md-center">
                    <div class="col-6">
                        <div class="float-left">
                            <button type="submit" class="btn btn-success" formmethod="post"
                                    formaction="${pageContext.request.contextPath}/app?accion=withdraw">
                                Retirar
                            </button>

                            <button type="submit" class="btn btn-success" formmethod="post"
                                    formaction="${pageContext.request.contextPath}/app?accion=deposit">
                                Depositar
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>