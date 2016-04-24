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
			$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})});
			
			$("form[name='add']")
			.form({
				fields:{
					roomNo: 'empty',
					type: 'empty',
					price: 'empty',
					size: 'empty',
					capacity: 'empty'
				},
				inline: true,
			});
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
					Add Room
				</div>
			</h1>
			<c:if test="${not empty sessionScope.errorMsg}">
				<div class="ui error message">
					${sessionScope.errorMsg}
				</div>
			</c:if>
			<form class="ui form" method="post" name="add" action="${pageContext.request.contextPath}/manage-room/add?add=1&hotel_id=${requestScope.hotelId}">
	  			
	  			<div class="eight wide field">
	  				<label>Room Number</label>
	 				<c:if test="${not empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Room Number" name="roomNo" value="${sessionScope.inputBefore.roomNo}"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Room Number" name="roomNo"/>
	  				</c:if>
	  			</div>
	  			
	  			<div class="eight wide field">
	  				<label>Room Type</label>
	 				<c:if test="${not empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Room Type" name="type" value="${sessionScope.inputBefore.type}"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore}">
	  					<input type="text" placeholder="Room Type" name="type"/>
	  				</c:if>
	  			</div>
	  			
	  			<div class="six wide field">
	  				<label>Price</label>
	  				<div class="ui right labeled input">
						<div class="ui label">
							$
						</div>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="text" placeholder="$0.00" name="price" value="${sessionScope.inputBefore.price }"/>
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="text" placeholder="$0.00" name="price"/>
		  				</c:if>
		  				<div class="ui basic label">.00</div>
	  				</div>
	  			</div>
	  			
	  			<div class="fields">
		  			<div class="six wide field">
		  				<label>Room Size (in m<sup>2</sup>)</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="number" min="1" value="${sessionScope.inputBefore.size}" placeholder="Room Size" name="size" />
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="number" min="1" placeholder="Room Size" name="size"/>
		  				</c:if>
		  			</div>
		  			
		  			<div class="six wide field">
		  				<label>Capacity</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="number" min="1" value="${sessionScope.inputBefore.capacity}" placeholder="Capacity" name="capacity" />
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="number" min="1" placeholder="Capacity" name="capacity"/>
		  				</c:if>
	  				</div>
	  			</div>
	  			
	  			<div class="fields">
		  			<div class="six wide field">
		  				<label>Discount (in %)</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="number" min="0" value="${sessionScope.inputBefore.discount}" placeholder="Discount" name="discount" />
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="number" min="0" placeholder="Discount" name="discount"/>
		  				</c:if>
		  			</div>
	  			
		  			<div class="six wide field">
		  				<label>Recommended</label>
		  				<c:if test="${not empty sessionScope.inputBefore}">
		  					<input type="number" min="0" value="${sessionScope.inputBefore.recommended}" placeholder="Recommended" name="recommended" />
		  				</c:if>
		  				<c:if test="${empty sessionScope.inputBefore}">
		  					<input type="number" min="0" placeholder="Recommended" name="recommended"/>
		  				</c:if>
		  			</div>
	  			</div>
	  			
	  			<button class="ui green button" type="submit">Create</button>
	  			<button class="ui reset button" type="reset">Reset</button>
	  			<div class="ui error message"></div>
	  		</form>
	  		
	  		<c:remove var="errorMsg" scope="session"/>
	  		<c:remove var="inputBefore" scope="session"/>
		</div>
	</div>
</body>
</html>