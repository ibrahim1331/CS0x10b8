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
			
			$('.ui.checkbox').checkbox();
				
			//hack
			$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
			
			$("form[name='update']")
			.form({
				fields:{
					name: 'empty',
					address: 'empty',
					location: 'empty',
					noOfRooms: 'number',
					description: 'empty'
				},
				inline: true,
			})
		})
	</script>
	
	<style>
	.room:hover{
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
					Update Hotel
				</div>
			</h1>
			<c:if test="${not empty sessionScope.errorMsg}">
				<div class="ui error message">
					${sessionScope.errorMsg}
				</div>
			</c:if>
			<div class="ui top attached tabular menu">
				<a class="active item" data-tab="hotel">Hotel</a>
				<a class="item" data-tab="rooms">Rooms</a>
			</div>
			<div class="ui bottom attached active tab segment" data-tab="hotel">
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
		  			
		  			<div class="field">
		  				<label>Facilities</label>
						<div class="ui blue segment grid">
						<c:forEach var="facility" items="${requestScope.facilities}">
								<div class="four wide column">
									<div class="field">
										<c:if test="${fn:length(requestScope.hotelFacilities) gt 0}">
											<c:set var="matched"  value="false"/>
											<c:forEach var="hotelFacility" items="${requestScope.hotelFacilities}">
												<c:if test="${hotelFacility.facility == facility.facilityId }">
													<c:set var="matched" value="true"/>
											    </c:if>
									   		</c:forEach>
									   		<c:if test="${matched eq true}">
										    	<div class="ui checkbox checked">
													<input name="facilities" type="checkbox" value="${facility.facilityId}" tabindex="0" class="hidden" checked>
													<label>${facility.name}</label>
											    </div>
										    </c:if>
										    <c:if test="${matched eq false}">
												<div class="ui checkbox">
													<input name="facilities" type="checkbox" value="${facility.facilityId}" tabindex="0" class="hidden">
													<label>${facility.name}</label>
											    </div>
										    </c:if>
									    </c:if>
									    <c:if test="${fn:length(requestScope.hotelFacilities) eq 0}">
											<div class="ui checkbox">
												<input name="facilities" type="checkbox" value="${facility.facilityId}" tabindex="0" class="hidden">
												<label>${facility.name}</label>
										    </div>
									    </c:if>
								   </div>
								</div>
						</c:forEach>
						</div>
					</div>
		  			
		  			<div class="twelve wide field">
		  				<label>Description</label>
	  					<textarea placeholder="Enter Description of Hotel" name="description">${requestScope.hotel.description}</textarea>
		  			</div>
		  			
		  			<button class="ui green button" type="submit">Update</button>
		  			<button class="ui reset button" type="reset">Reset</button>
		  			<div class="ui error message"></div>
		  		</form>
	  		</div>
	  		<div class="ui bottom attached tab segment" data-tab="rooms">
	  			<a href="${pageContext.request.contextPath }/manage-room/add?hotel_id=${requestScope.hotel.hotelId}" class="ui blue button">Add Room</a>
	  			<c:if test="${requestScope.rooms.size() > 0 }">
					<div class="ui vertical fluid menu">
						<c:forEach var="room" items="${requestScope.rooms}">
							<div class="item room" onclick="window.location = '${pageContext.request.contextPath}/manage-room/edit?room_id=${room.roomId}'">
								<div class="ui green label">${room.type}</div>
								<div class="content">
									<h1 class="ui header">${room.roomNo}</h1>
					  				<div class="inline field">
					  					<p>Size: ${room.size} m<sup>2</sup></p>
										<p>Price: HK$ ${room.price}</p>
					  				</div>
								</div>
								<div class="ui grid">
								<div class="fifteen wide column"> </div>
									<div class="one wide column">
										<a class="ui red label" href="${pageContext.request.contextPath}/manage-room/remove?hotel_id=${requestScope.hotel.hotelId}&room_id=${room.roomId}">
											<i class="large trash outline icon" style="margin: 0 !important;"></i>
										</a>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					</c:if>
	  		</div>
	  		<c:remove var="errorMsg" scope="session"/>
		</div>
	</div>
</body>
</html>