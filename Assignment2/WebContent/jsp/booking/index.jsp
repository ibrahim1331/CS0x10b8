<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - User</title>
<jsp:include page="../include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close').on('click', function() {
	    $(this).closest('.message').transition('fade');
	});
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
	$(".date").each(function(idx, ele){
		$(ele).text(new Date($(ele).text()).format("yyyy-mm-dd hh:MM:ss TT"))
	})
	
	$("form[name='byPin']").form({
		fields:{
			pin: ['empty', 'minLength[25]','maxLength[25]']
		},
		inline: true
	})
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<div class="ui basic segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="calendar outline icon"></i>
			<div class="content">
				Booking
				<div class="sub header">Find, make or modify your bookings here.</div>
			</div>
		</h1>
		<a class="ui blue button" href="${pageContext.request.contextPath}/booking/temp">Temp bookings</a>
		<div class="ui basic centered segment">
			<div class="ui two column very relaxed grid">
			  <div class="six wide column">
			  	<h3>Search by PIN</h3>
			    <form class="ui form" name="byPin" action="${pageContext.request.contextPath}/booking/record">
			    	<div class="field">
			    		<input type="text" name="pin" placeholder="PIN"/>
			    	</div>
			    	<button class="ui submit blue button">Search!</button>
			    </form>
			  </div>
			  <div class="ui vertical divider">
			    or
			  </div>
			  <div class="ten wide column">
			  	<h3>Search from your account</h3>
			    <div class="ui basic segment">
			    <c:if test="${empty sessionScope.loginUser }">
			    	You need to log in to see your bookings.
			    </c:if>
			    <c:if test="${not empty sessionScope.loginUser && empty requestScope.bookings}">
			    	No booking records
			    </c:if>
			    <c:if test="${not empty sessionScope.loginUser && not empty requestScope.bookings}">
			    	<div class="ui fluid vertical menu">
			    		<c:forEach var="record" items="${requestScope.bookings}">
			    			<a class="item" href="${pageContext.request.contextPath }/booking/record?number=${record.bookingNumber}">
			    				<h1 class="ui header">#${record.bookingNumber}</h1>
			    				<p>Booked in <span class="date">${record.bookingDate }</span></p>
			    			</a>
			    		</c:forEach>
			    	</div>
			    </c:if>
			    
			    </div>
			  </div>
			</div>
		</div>
		
		
	</div>
</div>

</body>
</html>