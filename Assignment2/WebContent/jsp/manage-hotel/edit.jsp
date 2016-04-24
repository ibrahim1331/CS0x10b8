<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hotel Go - Hotel</title>
	<jsp:include page="../include/include.jsp"></jsp:include>

	<script>
		$(document).ready(function(){
			$('.message .close').on('click', function() {
			    $(this).closest('.message').transition('fade');
			});
			
			$(".ui.dropdown").dropdown();
			
			$(".tabular.menu .item").tab();
			
			//hack
			$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
			
			$("form[name='update']")
			.form({
				fields:{
					name: 'empty',
					address: 'empty',
					location: 'empty',
					noOfRooms: 'empty',
					description: 'empty'
				},
				inline: true,
			})
		})
	</script>
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	
	<div class="ui attached segment">
		<div class="ui container">
			<h1 class="ui header">
				<i class="building icon"></i>
				<div class="content">
					Update Hotel
				</div>
			</h1>
			<c:if test="${not empty sessionScope.errorMsg}">
				<div class="ui error message">
					${sessionScope.errorMsg}
				</div>
			</c:if>
			<form class="ui form" method="post" name="update" action="${pageContext.request.contextPath}/manage-hotel/edit?edit=1&hotel_id=${requestScope.hotel.hotelId}">
	  			<div class="eight wide field">
	  				<label>Hotel Name</label>
  					<input type="text" placeholder="Hotel Name" name="name" value="${requestScope.hotel.name}"/>
	  			</div>
	  			
	  			<div class="fields">
		  			<div class="eight wide field">
		  				<label>Address</label>
	  					<input type="text" placeholder="Address" name="address" value="${requestScope.hotel.address}"/>
		  			</div>
		  			
		  			<div class="four wide field">
		  				<label>Location</label>
	  					<select class="ui search selection dropdown" name="location">
	  						<option></option>
	  						<c:forEach var="location" items="${requestScope.locations}">
	  							<option value="${location.locationId}" ${requestScope.hotel.location == location.locationId ? "selected":""}>${location.name}</option>
	  						</c:forEach>
	  					</select>
		  			</div>
	  			</div>
	  			
	  			<div class="four wide field">
	  				<label>No of Rooms</label>
  					<input type="text" placeholder="No Of Rooms" name="noOfRooms" value="${requestScope.hotel.noOfRooms}"/>
	  			</div>
	  			
	  			<div class="twelve wide field">
	  				<label>Description</label>
  					<textarea placeholder="Enter Description of Hotel" name="description">${requestScope.hotel.description}</textarea>
	  			</div>
	  			
	  			<button class="ui green button" type="submit">Update</button>
	  			<button class="ui reset button" type="reset">Reset</button>
	  			<div class="ui error message"></div>
	  		</form>
	  		
	  		<c:remove var="errorMsg" scope="session"/>
		</div>
	</div>
</body>
</html>