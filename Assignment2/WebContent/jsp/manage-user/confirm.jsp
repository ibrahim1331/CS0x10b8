<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - User</title>
<jsp:include page="../include/include.jsp"></jsp:include>
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
<jsp:include page="../include/header.jsp"></jsp:include>
<div class="ui attached segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="remove user icon"></i>
			<div class="content">
				Remove Hotel Managers
				<div class="sub header">You are going to remove the following manager.</div>
			</div>
		</h1>
		<div class="ui fluid vertical menu">
			<div class="item">
				<div class="content">
					<h1 class="ui medium header">${requestScope.manager.title} ${requestScope.manager.firstName} ${requestScope.manager.lastName}</h1>
					<p>Email: ${requestScope.manager.email}</p>
				</div>
			</div>
		</div>
		<p>Are you sure? This cannot be undone.</p>
		<a href="${pageContext.request.contextPath }/manage-user/remove?confirm=1&id=${requestScope.manager.userId}" class="ui green button">Confirm</a>
		<a href="${pageContext.request.contextPath }/manage-user" class="ui red button">Cancel</a>
	</div>
</div>

</body>
</html>