<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go</title>
<jsp:include page="../include/include.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<h1 class="ui centered header">General Error Page
<div class="sub header">Error Code: ${pageContext.errorData.statusCode}</div>
<div class="sub header">Error Message: ${requestScope['javax.servlet.error.message']}</div>
</h1>
<p></p>
</body>