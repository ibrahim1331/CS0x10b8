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
				Update booking ${requestScope.bookingView.roomNo}, ${requestScope.bookingView.hotelName }
				<div class="sub header">Change your booking information</div>
			</div>
		</h1>
		<form novalidate name="booking" class="ui form" method="post" action="${pageContext.request.contextPath }/booking/update?confirm=1&id=${requestScope.bookingView.bookingId}">
			<div class="four wide field">
				<label>Number of People (Max: ${requestScope.bookingView.roomCapacity})</label>
				<input type="number" min="1" max="${requestScope.bookingView.roomCapacity}" name="noOfPeople" value="${requestScope.bookingView.noOfPeople }"/>
			</div>
			<div class="two fields">
				<div class="four wide field">
					<div class="ui calendar" id="fromDate">
						<label>Check in Date</label>
						<input type="text" name="fromDate" value="${requestScope.bookingView.checkInDate }"/>
					</div>
				</div>
				<div class="four wide field">
					<div class="ui calendar" id="toDate">
						<label>Check out Date</label>
						<input type="text" name="toDate" value="${requestScope.bookingView.checkOutDate }" />
					</div>
				</div>
			</div>
			<div class="four wide field">
				<label>Purpose</label>
				<input type="text" name="purpose" value="${requestScope.bookingView.purpose }" maxlength="20"/>
			</div>
			
			<a href="${pageContext.request.contextPath }/booking/record?number=${requestScope.bookingView.bookingNumber}" class="ui button">Back</a>
			<button class="ui green submit button">Change</button>
			<button class="ui red submit button" formaction="${pageContext.request.contextPath }/booking/cancel?id=${requestScope.bookingView.bookingId}">Cancel</button>
		</form>
		
		
	</div>
</div>

</body>
</html>