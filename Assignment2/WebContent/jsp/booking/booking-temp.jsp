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
	
	$(".date").each(function(idx, ele){
		$(ele).text(new Date($(ele).text()).format("yyyy-mm-dd hh:MM:ss TT"))
	})
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<div class="ui basic segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="calendar outline icon"></i>
			<div class="content">
				Temporary booking list
			</div>
		</h1>
		<a href="${pageContext.request.contextPath }/booking/" class="ui button">Back</a>
		<c:if test="${not empty sessionScope.failAdd }">
			<div class="ui error message">
				<i class="close icon"></i>
				<div class="content">
					<p>The bookings cannot be completed. Please revise.</p>
				</div>
			</div>
			<c:remove var="failAdd" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.successRemove }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking is removed.</p>
				</div>
			</div>
			<c:remove var="successRemove" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.successUpdate }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking is successfully updated.</p>
				</div>
			</div>
			<c:remove var="successUpdate" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.successAdd }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking is successfully added.</p>
				</div>
			</div>
			<c:remove var="successAdd" scope="session"/>
		</c:if>
		<div class="ui basic segment">
			<c:if test="${empty sessionScope.tempBookings }">
				<p>No bookings</p>
			</c:if>
			<c:if test="${not empty sessionScope.tempBookings }">
			<c:set var="totalPrice" value="${0}"/>
			<div class="ui vertical fluid menu">
				<c:forEach var="booking" items="${sessionScope.tempBookings }" varStatus="loop">
					<c:set var="totalPrice" value="${totalPrice + booking.price}"/>
					<a class="item" href="${pageContext.request.contextPath}/booking/temp/update?i=${loop.index}">
						<div class="ui basic segment">
							<h3 class="ui left floated header">${booking.roomNo}, ${booking.hotelName}</h3>
							<h1 class="ui right floated header">$${booking.price }</h1>
						</div>
						<div class="ui basic segment">
							<p>For ${booking.noOfPeople} person(s)</p>
							<p>From <span class="date">${booking.checkInDate}</span> to <span class="date">${booking.checkOutDate}</span></p>
							<p>Purpose: ${booking.purpose}</p>
						</div>
						
					</a>
				</c:forEach> 
			</div>
			<p>Total Price: $${totalPrice}</p>
			<a class="ui blue button" href="${pageContext.request.contextPath}/booking/createAll">Reserve All</a>
			
			</c:if>
		</div>
		
		
	</div>
</div>

</body>
</html>