<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - User</title>
<jsp:include page="include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close').on('click', function() {
	    $(this).closest('.message').transition('fade');
	});
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
<div class="ui attached segment">
	<%-- <pre>Users: ${requestScope.users }</pre> --%>
	<table class="ui celled table">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${requestScope.users}">
				<tr>
					<td>${user.firstName }</td>
					<td>${user.lastName }</td>
					<td>${user.email }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

</body>
</html>