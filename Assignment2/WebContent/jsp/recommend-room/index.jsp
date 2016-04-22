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
			<i class="thumbs up icon"></i>
			<div class="content">
				Recommend Rooms
				<div class="sub header">Select a hotel first.</div>
			</div>
		</h1>
		<div class="ui fluid vertical menu">
			<c:forEach var="hotel" items="${requestScope.hotels}">
				<a class="item" href="${pageContext.request.contextPath}/recommend-room/rooms?hotel=${hotel.hotelId}">
					<div class="ui blue label">${hotel.rating}/10</div>
					<div class="ui blue label">${hotel.noOfRooms} rooms</div>
					<div class="content">
						<h1 class="ui header">${hotel.name}</h1>
						<p>${hotel.address}</p>
					</div>
				</a>
			</c:forEach>
		</div>
	</div>
</div>

</body>
</html>