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
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
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
	<table class ="ui celled table">
		<thead>
			<tr>
				<th>Hotel Name</th>
				<th>Location</th>
				<th>No of rooms</th>
				<th>Address</th>
				<th>Rating</th>
				<th> </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="hotel" items="${requestScope.hotels}">
				<tr>
					<td><c:out value="${hotel.name}"></c:out></td>
					<td><c:out value="${hotel.location}"></c:out></td>
					<td><c:out value="${hotel.noOfRooms}"></c:out></td>
					<td><c:out value="${hotel.address}"></c:out></td>
					<td><c:out value="${hotel.rating}"></c:out></td>
					<td><a href="roomResult?id=${hotel.hotelId}" class="ui button">View More</a> </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

</body>
</html>