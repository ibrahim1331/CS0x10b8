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
				Booking #${requestScope.bookingNumber} details
				<div class="sub header">Review your booking record.</div>
			</div>
		</h1>
		<c:if test="${not empty sessionScope.failCancel }">
			<div class="ui error message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking cannot be cancel... WAT?</p>
				</div>
			</div>
			<c:remove var="failCancel" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.successCancel }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking is successfully cancelled</p>
				</div>
			</div>
			<c:remove var="successCancel" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.failUpdate }">
			<div class="ui error message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking cannot be updated because the room is not available within the changed date range</p>
				</div>
			</div>
			<c:remove var="failUpdate" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.successUpdate }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<p>The booking is successfully updated</p>
				</div>
			</div>
			<c:remove var="successUpdate" scope="session"/>
		</c:if>
		<a class="ui button" href="${pageContext.request.contextPath}/booking/">Back</a>
		<div class="ui basic segment">
			<div class="ui vertical fluid menu">
				<c:set var="totalPrice" value="${0}"/>
				<c:forEach var="booking" items="${requestScope.bookingView }">
					<c:set var="totalPrice" value="${totalPrice + (booking.isCancelled?0:booking.bookingPrice)}"/>
					<a class="item" href="${pageContext.request.contextPath}/booking/update?id=${booking.bookingId}" ${booking.isCancelled?'disabled':''}>
						<div class="ui basic segment">
							<h3 class="ui left floated header">${booking.roomNo }, ${booking.hotelName}</h3>
							<c:if test="${booking.isCancelled }">
								<h3 class="ui left floated header">(Cancelled)</h3>
							</c:if>
							<h2 class="ui right floated header">$${booking.bookingPrice}</h2>
						</div>
						<div class="ui basic segment">
							<p>For ${booking.noOfPeople } person(s)</p>
							<p>From <span class="date">${booking.checkInDate}</span> to <span class="date">${booking.checkOutDate }</span></p>
							<p>Purpose: ${booking.purpose }</p>
						</div>
					</a>
				</c:forEach>
			</div>
			<h3 class="ui header">Total Price: $${totalPrice}</h3>
		</div>
	</div>
</div>

</body>
</html>