<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Room Result</title>
<jsp:include page="include/include.jsp"></jsp:include>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
	<h1 class="ui centered header">Room Result</h1>
	<table class ="ui celled table">
		<thead>
			<tr>
				<th>Room No</th>
				<th>Type</th>
				<th>Price</th>
				<th>Capacity</th>
				<th>Discount</th>
				<th>Priority</th>
				<th> </th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${requestScope.recommendingRooms}" var="room">
					<tr>
						<td>${room.roomNo}</td>
						<td>${room.type}</td>
						<td>${room.price}</td>
						<td>${room.capacity}</td>
						<td>${room.discount}</td>
						<td>${room.recommended}</td>
						<td><a href="${pageContext.request.contextPath }/booking/create?id=${room.roomId}" class="ui button">Book</a> </td>
					</tr>
				</c:forEach>
				<c:forEach items="${requestScope.nonRecommendingRooms}" var="room">
					<tr>
						<td>${room.roomNo}</td>
						<td>${room.type}</td>
						<td>${room.price}</td>
						<td>${room.capacity}</td>
						<td>${room.discount}</td>
						<td>${room.recommended}</td>
						<td><a href="${pageContext.request.contextPath }/booking/create?id=${room.roomId}" class="ui button">Book</a> </td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
</body>
</html>