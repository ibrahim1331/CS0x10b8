<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close').on('click', function() {
	    $(this).closest('.message').transition('fade');
	});
	
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
	
	$(".description").each(function(idx, ele){
		$(ele).text($.trim($(ele).text()).substring(0, 100)
			    .split(" ").slice(0, -1).join(" ") + "...")
	})
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
<title>Result</title>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
<div class="ui attached segment">
	<h1 class="ui centered header">Result</h1>
		<div class="ui container">
			<form class="ui form" action="search" method="post">
				<div class="ui two column centered grid">
					<div class="centered row">
						<div class="column">
							<div class="ui fluid action input">
								<input type="text" name="city" placeholder="Search..." required value="${requestScope.result}"/>
								<button type="submit" class="ui button" >Search</button>	
							</div>
						</div>
					</div>
					<div class="row">
						<div class="column">
							<p>Check-in date</p>
							<div class="ui calendar" id="fromDate">
								<div class="ui fluid input left icon">
									<i class="calendar icon"></i>
									<input type="text" placeholder="From" name="fromDate" value="${requestScope.fromDate }"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="column">
							<p>Check-out date</p>
							<div class="ui calendar" id="toDate">
								<div class="ui fluid input left icon">
									<i class="calendar icon"></i>
									<input type="text" placeholder="To" name="toDate" value="${requestScope.toDate }"/>
								</div>
							</div>					
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="ui grid">
		<div class="fourteen wide centered column">
			<div class="ui fluid link cards">
				<c:forEach var="hotel" items="${requestScope.hotels}">
				<div class="card">
					<div class="image">
						<img src="${pageContext.request.contextPath}/resources/image/hotel.jpg">
					</div>
					<div class="content">
						<div class="header">
							<a href="roomResult?id=${hotel.hotelId}">${hotel.name}</a>
						</div>
						<div class="description">
							${hotel.description}
						</div>
					</div>
					<div class="extra content">
						<span class="right floated">
							${hotel.rating} / 10
						</span>
						<span>
							${hotel.noOfRooms} rooms
						</span>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
</div>

</body>
</html>