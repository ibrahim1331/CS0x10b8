<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Go - Hotel</title>
<jsp:include page="../include/include.jsp"></jsp:include>

<style>
	.item:hover{
		cursor: pointer;
	}
</style>
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	
	<div class="ui attached segment">
		<div class="ui container">
			<h1 class="ui header">
				<i class="building icon"></i>
				<div class="content">
					Manage Hotels
					<div class="sub header">Add, Edit or Remove hotels.</div>
				</div>
			</h1>
			<a href="${pageContext.request.contextPath }/manage-hotel/add" class="ui blue button">Add hotel</a>
			<div class="ui fluid vertical menu">
				<c:forEach var="hotel" items="${requestScope.hotels}">
					<div class="item" onclick="window.location = '${pageContext.request.contextPath}/manage-hotel/edit?hotel_id=${hotel.hotelId}'">
						<div class="ui blue label">
							${hotel.rating}/10
						</div>
						<div class="ui blue label">
							${hotel.noOfRooms} rooms
						</div>
						<div class="content">
							<h1 class="ui header">${hotel.name}</h1>
							<p>${hotel.address}</p>
						</div>
						<div class="ui grid">
							<div class="fifteen wide column"> </div>
							<div class="one wide column">
								<a class="ui red label" href="${pageContext.request.contextPath}/manage-hotel/remove?hotel_id=${hotel.hotelId}">
									<i class="large trash outline icon" style="margin: 0 !important;"></i>
								</a>
							</div>
						</div>
					</div>	
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>