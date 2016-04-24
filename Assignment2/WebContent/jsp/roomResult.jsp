<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Room Result</title>
<jsp:include page="include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close').on('click', function() {
	    $(this).closest('.message').transition('fade');
	});
	
	$("form[name='daterange']")
	.form({
		fields:{
			fromDate: 'empty',
			toDate: 'empty'	
		},
		inline: true
	})
	
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
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
<div class="ui basic segment">
	<div class="ui container">
		<div class="ui items">
			<div class="item">
				<div class="image">
					<img src="${pageContext.request.contextPath }/resources/image/hotel.jpg"/>
				</div>
				<div class="content">
					<h3 class="header">${requestScope.hotel.name }</h3>
					<div class="meta">
						<span class="address">${requestScope.hotel.address }</span>
					</div>
					<div class="description">
						<p>${requestScope.hotel.description }</p>
					</div>
					<div class="extra">
						<div class="ui label">${requestScope.hotel.noOfRooms} rooms</div>
						<div class="ui label">${requestScope.hotel.rating}/10</div>
					</div>
				</div>
			</div>
		</div>
		<form class="ui form" name="daterange" action="${pageContext.request.contextPath }/roomResult?id=${requestScope.hotel.hotelId}" method="post">
			<div class="inline fields">
				<div class="ui calendar field" id="fromDate">
					<label>Check-in Date</label>
					<input type="text" name="fromDate" value="${requestScope.fromDate}"/>
				</div>
				<div class="ui calendar field" id="toDate">
					<label>Check-out Date</label>
					<input type="text" name="toDate" value="${requestScope.toDate}"/>
				</div>
					<button class="ui button">Search</button>
			</div>
		</form>
		<c:if test="${requestScope.recommendingRooms.size() eq 0 && requestScope.nonRecommendingRooms.size() eq 0}">
			<h3 class="ui header">No rooms</h3>
		</c:if>
		<c:if test="${requestScope.recommendingRooms.size() > 0 || requestScope.nonRecommendingRooms.size() > 0}">
			<h3 class="ui header">Available rooms</h3>
			<div class="ui vertical fluid menu">
				<c:if test="${requestScope.recommendingRooms.size() > 0}">
					<c:forEach var="room" items="${requestScope.recommendingRooms }">
					<a class="item" href="${pageContext.request.contextPath }/booking/create?id=${room.roomId}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}">
						<div class="ui green label">${room.capacity} person(s)</div>
						<div class="ui green label">${room.type}</div>
						<div class="ui red label"><i class="star icon"></i>Featured</div>
						<div class="content">
							<h3 class="ui header">${room.roomNo}</h3>
							<p>Original price: $${room.price}</p>
							<p>Discount price: $${room.price * room.discount /100}</p>
						</div>
					</a>
					</c:forEach>
				</c:if>	
				<c:if test="${requestScope.nonRecommendingRooms.size() != 0}">
					<c:forEach var="room" items="${requestScope.nonRecommendingRooms }">
					<a class="item" href="${pageContext.request.contextPath }/booking/create?id=${room.roomId}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}">
						<div class="ui green label">${room.capacity} person(s)</div>
						<div class="ui green label">${room.type}</div>
						<div class="content">
							<h3 class="ui header">${room.roomNo}</h3>
							<p>Original price: $${room.price}</p>
							<p>Discount price: $${room.price * room.discount /100}</p>
						</div>
					</a>
					</c:forEach>
				</c:if>
			</div>
		</c:if>
	</div>
</div>
</body>
</html>