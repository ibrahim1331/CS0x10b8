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
			
			$("form[name='add']")
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
					Add new Hotel
				</div>
			</h1>
			<c:if test="${not empty sessionScope.errorMsg}">
				<div class="ui error message">
					${sessionScope.errorMsg}
				</div>
			</c:if>
			<form class="ui form" method="post" name="add" action="${pageContext.request.contextPath}/manage-hotel/add?add=1">
	  			<div class="eight wide field">
	  				<label>Hotel Name</label>
	 				<c:if test="${not empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Hotel Name" name="name" value="${sessionScope.inputBefore.name}"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Hotel Name" name="name"/>
	  				</c:if>
	  			</div>
	  			
	  			<div class="fields">
		  			<div class="eight wide field">
		  				<label>Address</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="text" placeholder="Address" name="address" value="${sessionScope.inputBefore.address }"/>
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="text" placeholder="Address" name="address"/>
		  				</c:if>
		  			</div>
		  			
		  			<div class="four wide field">
		  				<label>Location</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<select class="ui search selection dropdown" name="location">
		  						<option></option>
		  						<c:forEach var="location" items="${requestScope.locations}">
		  							<option value="${location.locationId}" ${not empty sessionScope.inputBefore.location && sessionScope.inputBefore.location == location.locationId ?"selected":""}>${location.name}</option>
		  						</c:forEach>
		  					</select>
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<select class="ui search selection dropdown" name="location">
		  						<option></option>
		  						<c:forEach var="location" items="${requestScope.locations}">
		  							<option value="${location.locationId}">${location.name}</option>
		  						</c:forEach>
		  					</select>
		  				</c:if>
		  			</div>
	  			</div>
	  			
	  			<div class="four wide field">
	  				<label>No of Rooms</label>
	  				<c:if test="${not empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="No Of Rooms" name="noOfRooms" value="${sessionScope.inputBefore.noOfRooms}"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="No Of Rooms" name="noOfRooms"/>
	  				</c:if>
	  			</div>
	  			
	  			<div class="twelve wide field">
	  				<label>Description</label>
	  				<c:if test="${not empty sessionScope.inputBefore}">
	  					<textarea placeholder="Enter Description of Hotel" name="description">${sessionScope.inputBefore.description}</textarea>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore}">
	  					<textarea type="text" placeholder="Enter Description of Hotel" name="description"></textarea>
	  				</c:if>
	  			</div>
	  			
	  			<button class="ui green button" type="submit">Create</button>
	  			<button class="ui reset button">Reset</button>
	  			<div class="ui error message"></div>
	  		</form>
	  		
	  		<c:remove var="errorMsg" scope="session"/>
	  		<c:remove var="inputBefore" scope="session"/>
		</div>
	</div>
</body>
</html>