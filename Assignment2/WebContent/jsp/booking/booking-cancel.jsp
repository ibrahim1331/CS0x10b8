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
	
	$("#fromDate").calendar({
		endCalendar: $("#toDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		}
	});
	$("#toDate").calendar({
		startCalendar: $("#fromDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		} 
	})
	
	$("form[name='booking']")
	.form({
		fields:{
			noOfPeople: ['empty','integer[1..${requestScope.roomView.roomCapacity}]'],
			fromDate: 'empty',
			toDate: 'empty',
			purpose: 'empty'
		},
		inline: true
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
				Cancel booking
				<div class="sub header">You are going to cancel the booking.</div>
			</div>
		</h1>
		<form novalidate name="booking" class="ui form" method="post" action="${pageContext.request.contextPath }/booking/cancel?confirm=1&id=${requestScope.bookingView.bookingId}">
			<div class="ui basic segment">
				<p>Hotel: ${requestScope.bookingView.hotelName}</p>
				<p>Room: ${requestScope.bookingView.roomNo }</p>
				<p>For ${requestScope.bookingView.noOfPeople } person(s)</p>
				<p>From <span class="date">${requestScope.bookingView.checkInDate }</span> to <span class="date">${requestScope.bookingView.checkOutDate }</span></p>
				<p>Purpose: ${requestScope.bookingView.purpose }</p>
			</div>
			
			<p>Confirm cancellation? This cannot be undone.</p>
			<a href="${pageContext.request.contextPath }/booking/record?number=${requestScope.bookingView.bookingNumber}" class="ui button">Back</a>
			<button class="ui green submit button">Confirm</button>
		</form>
		
		
	</div>
</div>

</body>
</html>