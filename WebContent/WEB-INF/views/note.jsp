<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 09.03.19
  Time: 1:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MEMO</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container">
<h2>${note.name}</h2>
<img>
<p>${note.body}</p>
    <footer>${note.createDate}</footer>
    <img width="100" height="100" src="/photo/${note.id}"></img>
</blockquote>
</div>
</body>
</html>
