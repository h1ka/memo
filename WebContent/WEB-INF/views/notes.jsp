<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>NOTES</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a>
        </h2>

        <form id="search" method="GET" action="${contextPath}/search">
            <input type="text" name="name"  class="form-control" placeholder="Search memo">
        </form>

        <form id="createNoteForm" method="GET" action="${contextPath}/create">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Your notes | <a onclick="document.forms['createNoteForm'].submit()">New MEMO</a>
        </h2>



    </c:if>
    <c:if test="${not empty notes }">
        <div >
        <table class="table table-hover">
            <tr>
                <td>photo</td>
                <td>Note Name</td>
                <td>Note Body</td>
                <td>Note Create date</td>
                <td>Action</td>
            </tr>
            <c:forEach var="note" items="${notes}">
                <tr>
                    <td><img src="/photo/${note.id}"></img></td>
                    <td>${note.name }</td>
                    <td>${note.body }</td>
                    <td>${note.createDate}</td>
                    <td>
                        <a href="${contextPath}/notes/${note.id}">Show</a>
                        <a href="${contextPath}/${note.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </div>
    </c:if>

</div>
</body>
</html>