<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container px-4 mt-3">
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col" class="table-secondary">Registro de transacciones</th>
            <th scope="col" class="table-secondary">Detalle</th>
            <th scope="col" class="table-secondary">Monto</th>
        </tr>
        </thead>
        <c:forEach items="${account.transactions}" var="transaction">
            <thead>
            <tr>
                <th scope="col" class="table-light">${transaction.name}</th>
                <th scope="col" class="table-light">${transaction.associatedDetail}</th>
                <c:choose>
                    <%--                        <c:when test="${transaction.name.contains('DEPOSITO')}">--%>
                    <c:when test="${transaction.isPositive()}">
                        <th scope="col" class="table-primary">${transaction.value}</th>
                    </c:when>
                    <c:otherwise>
                        <th scope="col" class="table-danger">${transaction.value}</th>
                    </c:otherwise>
                </c:choose>
            </tr>
            </thead>
        </c:forEach>
        <thead>
        <tr>
            <th scope="col" class="table-secondary">Saldo</th>
            <th scope="col" class="table-secondary" ></th>
            <th scope="col" class="table-warning">${account.balance}</th>
        </tr>
        </thead>
    </table>
</div>
