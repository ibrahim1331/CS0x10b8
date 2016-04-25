<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hotel Go - Room</title>
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
					roomNo: 'empty',
					type: 'empty',
					price: 'empty',
					size: 'empty',
					capacity: 'empty'
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
					Update Room
				</div>
			</h1>
			<c:if test="${not empty sessionScope.errorMsg}">
				<div class="ui error message">
					${sessionScope.errorMsg}
				</div>
			</c:if>
			
			<form class="ui form" method="post" name="update" action="${pageContext.request.contextPath}/manage-room/edit?edit=1&room_id=${requestScope.room.roomId}">
	  			<div class="eight wide field">
  				<label>Room Number</label>
				<input type="text" placeholder="Room Number" name="roomNo" value="${requestScope.room.roomNo}"/>
  			</div>
  			
  			<div class="eight wide field">
  				<label>Room Type</label>
				<input type="text" placeholder="Room Type" name="type" value="${requestScope.room.type}"/>
  			</div>
  			
  			<div class="six wide field">
  				<label>Price</label>
  				<div class="ui right labeled input">
					<div class="ui label">
						$
					</div>
  					<input type="text" placeholder="$0.00" name="price" value="${requestScope.room.price }"/>
	  				<div class="ui basic label">.00</div>
  				</div>
  			</div>
  			
  			<div class="fields">
	  			<div class="six wide field">
	  				<label>Room Size (in m<sup>2</sup>)</label>
  					<input type="number" min="1" value="${requestScope.room.size}" placeholder="Room Size" name="size" />
	  			</div>
	  			
	  			<div class="six wide field">
	  				<label>Capacity</label>
  					<input type="number" min="1" value="${requestScope.room.capacity}" placeholder="Capacity" name="capacity" />
  				</div>
  			</div>
  			
  			<div class="fields">
	  			<div class="six wide field">
	  				<label>Discount (in %)</label>
  					<input type="number" min="0" value="${requestScope.room.discount}" placeholder="Discount" name="discount" />
	  			</div>
  			
	  			<div class="six wide field">
	  				<label>Recommended</label>
  					<input type="number" min="0" value="${requestScope.room.recommended}" placeholder="Recommended" name="recommended" readonly/>
	  			</div>
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