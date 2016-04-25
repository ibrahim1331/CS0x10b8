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
		<div class="ui text container">
			<div class="ui one column grid">
				<div class="column">
					<h1 class="ui centered header">Success! Your booking is created.</h1>
					<p class="ui centered header">The PIN number refering to this booking record is :</>
					<h1 class="ui centered header">${requestScope.pin}</h1>
					<p>Please remember the PIN. You can use this to find the exact booking record if you need to modify or cancel in the future.</p>
					<c:if test="${not empty requestScope.failed && !requestScope.failed.isEmpty()}">
						<p>${requestScope.failed.size()} reservation(s) cannot be done because the room is already reserved by other users.</p>
					</c:if>			
				</div>
			</div>
		</div>
		
		
	</div>
</div>

</body>
</html>