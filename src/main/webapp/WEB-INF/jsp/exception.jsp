<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container text-center">
        <br>
        <h4 class="my-3">${status}</h4>
        <h2>${typeMessage}</h2>
        <h4 class="my-5">${message}</h4>
    </div>
</div>
<%--
    Стек-трейс исключения отобразим в блоке комментариев <!-- ... --> - такие комментарии не будут видны пользователю,
    но их можно будет увидеть в исходном коде страницы (решение скорее для тестового приложения, тк на продакшене обычно
    скрываются детали реализации).
--%>
<!--
<c:forEach items="${exception.stackTrace}" var="stackTrace">
    ${stackTrace}
</c:forEach>
-->
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>