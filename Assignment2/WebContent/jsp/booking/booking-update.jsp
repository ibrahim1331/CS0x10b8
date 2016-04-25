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
	
	$(".ui.dropdown.room").dropdown({
		onChange: function(value){
			checkRoom({roomId: parseInt(value)});
			changeRoom(parseInt(value));
		}
	});
	
	$(".tabular.menu .item").tab();
	
	$("#fromDate")
	.calendar({
		endCalendar: $("#toDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		},
		onChange: function(date){
			checkRoom({fromDate: date.format("yyyy-mm-dd hh:MM:ss TT")})
		}
	});
	$("#toDate")
	.calendar({
		startCalendar: $("#fromDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		},
		onChange: function(date){
			checkRoom({toDate: date.format("yyyy-mm-dd hh:MM:ss TT")})
		}
	});
	
	$("form[name='booking']")
	.form({
		fields:{
			roomId: 'empty',
			noOfPeople: ['empty','integer[1..${requestScope.roomView.roomCapacity}]'],
			fromDate: 'empty',
			toDate: 'empty',
			purpose: 'empty'
		},
		inline: true
	})
	
	$("#checkRoom").on("click",checkRoom)
	
	function changeRoom(roomId){
		var data = {roomId: roomId};
		$.ajax({
			url: "${pageContext.request.contextPath}/rest/booking/getRoom",
			method: "post",
			data: JSON.stringify(data),
			dataType: "json"
		}).then(function(data){
			$("#maxNoOfPeople").text(data.roomCapacity);
			$("input[name='noOfPeople']").attr("max",data.roomCapacity);
			$("#price").text(data.roomPrice * data.discount / 100)
		})
	}
	
	function checkRoom(obj){
		var form = $("form[name='booking']");
		var data = {
			bookingId: ${requestScope.bookingView.bookingId},
			roomId: obj&&obj.roomId?obj.roomId:form.form("get value", "roomId"),
			checkInDate: obj&&obj.fromDate?obj.fromDate:form.form("get value", "fromDate"),
			checkOutDate: obj&&obj.toDate?obj.toDate:form.form("get value", "toDate")
		};
		$.ajax({
			url: "${pageContext.request.contextPath}/rest/booking/checkAvailable",
			method: "post",
			data: JSON.stringify(data),
			dataType: "json"
		}).then(function(data){
			$("#checkRoomMessage").css("display","inline");
			if(data.available){
				$("#checkRoomMessage > i").addClass("green checkmark");
				$("#checkRoomMessage > i").removeClass("red remove");
				$("#checkRoomMessage > span").text(data.msg);
				$(".change.submit.button").removeClass("disabled");
			} else {
				$("#checkRoomMessage > i").removeClass("green checkmark");
				$("#checkRoomMessage > i").addClass("red remove");
				$("#checkRoomMessage > span").text(data.msg);
				$(".change.submit.button").addClass("disabled");
			}
			
		})
	}
	
	checkRoom();
	
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
			<div class="two fields">
				<div class="four wide field">
					<label>Room</label>
					<select class="ui dropdown room" name="roomId">
						<c:forEach var="room" items="${rooms}">
						<option value="${room.roomId}" ${room.roomId eq bookingView.roomId?'selected':''}>${room.roomNo} (${room.type})</option>
						</c:forEach>
					</select>
				</div>
				<div class="four wide field">
					<label>Number of People (Max: <span id="maxNoOfPeople">${requestScope.bookingView.roomCapacity}</span>)</label>
					<input type="number" min="1" max="${requestScope.bookingView.roomCapacity}" name="noOfPeople" value="${requestScope.bookingView.noOfPeople}"/>
				</div>
			</div>
			<div class="two fields">
				<div class="four wide ui calendar field" id="fromDate">
					<label>Check in Date</label>
					<div class="ui left icon input">
						<i class="calendar icon"></i>
						<input type="text" name="fromDate" value="${requestScope.bookingView.checkInDate }"/>
					</div>
				</div>
				<div class="four wide ui calendar field" id="toDate">
					<label>Check out Date</label>
					<div class="ui left icon input">
						<i class="calendar icon"></i>
						<input type="text" name="toDate" value="${requestScope.bookingView.checkOutDate }" />
					</div>
				</div>
			</div>
			<div class="four wide field">
				<label>Purpose</label>
				<input type="text" name="purpose" value="${requestScope.bookingView.purpose }" maxlength="20"/>
			</div>
			<p>Price: $<span id="price">${bookingView.bookingPrice}</span></p>
			
			<a href="${pageContext.request.contextPath }/booking/record?number=${requestScope.bookingView.bookingNumber}" class="ui button">Back</a>
			<button class="ui green change submit button">Change</button>
			<a class="ui red button" href="${pageContext.request.contextPath }/booking/cancel?id=${requestScope.bookingView.bookingId}">Cancel Booking</a>
			<span id="checkRoomMessage" style="display:none">
				<i class="red remove icon"></i>
				<span></span>
			</span>
		</form>
		
		
	</div>
</div>

</body>
</html>