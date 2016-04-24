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
				Change booking information
				<div class="sub header">Change your booking and fill in required information</div>
			</div>
		</h1>
		<form novalidate name="booking" class="ui form" method="post" action="${pageContext.request.contextPath }/booking/temp/update?confirm=1&i=${requestScope.i}">
			<div class="four wide field">
				<label>Number of People (Max: ${requestScope.roomView.roomCapacity})</label>
				<input type="number" min="1" max="${requestScope.roomView.roomCapacity}" name="noOfPeople" value="${requestScope.booking.noOfPeople }"/>
			</div>
			<div class="two fields">
				<div class="four wide field">
					<div class="ui calendar" id="fromDate">
						<label>Check in Date</label>
						<input type="text" name="fromDate" value="${requestScope.booking.checkInDate }"/>
					</div>
				</div>
				<div class="four wide field">
					<div class="ui calendar" id="toDate">
						<label>Check out Date</label>
						<input type="text" name="toDate" value="${requestScope.booking.checkOutDate }" />
					</div>
				</div>
			</div>
			<div class="four wide field">
				<label>Purpose</label>
				<input type="text" name="purpose" value="${requestScope.bookingView.purpose }" maxlength="20"/>
			</div>
		
			<a class="ui button" href="${pageContext.request.contextPath }/booking/temp">Back</a>
			<button class="ui green submit button">Change</button>
			<button class="ui red submit button" formaction="${pageContext.request.contextPath}/booking/temp/remove?i=${requestScope.i}">Remove</button>
		</form>
		
		
	</div>
</div>

</body>
</html>