<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="include/include.jsp"></jsp:include>
<title>Result</title>
</head>
<body>
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
				<tr>
					<td><c:out value="${hotel.name}"></c:out></td>
					<td><c:out value="${hotel.location}"></c:out></td>
					<td><c:out value="${hotel.noOfRooms}"></c:out></td>
					<td><c:out value="${hotel.address}"></c:out></td>
					<td><c:out value="${hotel.rating}"></c:out></td>
					<td><a href="roomResult?id=${hotel.hotelId}" class="ui button">View More</a> </td>
				</tr>
		</tbody>
	</table>
</div>

</body>
</html>