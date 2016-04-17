<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<div class="ui container">
		<h1 class="ui header">
			<i class="add user icon"></i>
			<div class="content">
				Manage Hotel Managers
				<div class="sub header">Add or remove hotel managers here.</div>
			</div>
		</h1>
		<%-- <table class="ui celled table">
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
		</table> --%>
		<c:if test="${fn:length(requestScope.users) le 1 }">
			<div class="ui warning message">
				<p>You only have one hotel manager. You should only remove him if you have more than one hotel manager.</p>
			</div>
		</c:if>
		<a href="#" class="ui blue button">Add hotel manager</a>
		<div class="ui fluid vertical menu">
			<c:forEach var="user" items="${requestScope.users}">
				<div class="item">
					<a class="ui red label">
						Remove
					</a>
					<div class="content">
						<h1 class="ui medium header">${user.title} ${user.firstName} ${user.lastName}</h1>
						<p>Email: ${user.email}</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

</body>
</html>