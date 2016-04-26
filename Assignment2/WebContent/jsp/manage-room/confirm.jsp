<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - Room</title>
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
			<i class="remove icon"></i>
			<div class="content">
				Remove Room
				<div class="sub header">You are going to remove the following room.</div>
			</div>
		</h1>
		<div class="ui fluid vertical menu">
			<div class="item">
				<div class="content">
					<h1 class="ui medium header">${requestScope.room.roomNo}</h1>
					<p>Type: ${requestScope.room.type}</p>
					<p>Price: ${requestScope.room.price}</p>
					<p>Capacity: ${requestScope.room.capacity}</p>
					<p>Size: ${requestScope.room.size}</p>
				</div>
			</div>
		</div>
		<p>Are you sure? This cannot be undone.</p>
		<a href="${pageContext.request.contextPath }/manage-room/remove?confirm=1&hotel_id=${requestScope.hotelId}&room_id=${requestScope.room.roomId}" class="ui green button">Confirm</a>
		<a href="${pageContext.request.contextPath }/manage-hotel/edit?hotel_id=${requestScope.hotelId}" class="ui red button">Cancel</a>
	</div>
</div>

</body>
</html>