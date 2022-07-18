<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${listAccounts}" var="account">
    <div class="col">
        <div class="card h-100">
            <div class="card-body p-4">
                <h1 class="card-title">${account.name}</h1>
                <a href="${pageContext.request.contextPath}/app?accion=show&id=${account.id}"
                   class="btn btn-primary btn-block w-100">Ingresar</a>
            </div>
        </div>
    </div>
</c:forEach>