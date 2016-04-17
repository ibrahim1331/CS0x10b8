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
<h1 class="ui centered header">Wow! Bad stuff happens in the server!
<div class="sub header">We cannot handle your request because there is something going wrong in the server. You may want to try it later.</div>
<c:choose>
	<c:when test="${not empty requestScope['javax.servlet.error.message'] }">
		<div class="sub header">Error Message: ${requestScope['javax.servlet.error.message']}</div>
	</c:when>
	<c:otherwise>
		<div class="sub header">Error Message: <pre>${pageContext.errorData.throwable.message }</pre></div>
	</c:otherwise>
</c:choose>
</h1>
<p></p>
</body>