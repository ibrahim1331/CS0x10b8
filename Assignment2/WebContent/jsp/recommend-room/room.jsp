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
	
	$("form").form({
		fields:{
			recommend: ['empty','integer[0...]']
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
<div class="ui attached segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="thumbs up icon"></i>
			<div class="content">
				Recommend Rooms for "${requestScope.hotel.name}"
				<div class="sub header">Change the recommend priority. Recommending rooms are always on top of the search based on priority.</div>
			</div>
		</h1>
		<c:if test="${not empty sessionScope.errorMsg}">
			<div class="ui error message">
				<i class="close icon"></i>
				<div class="content">
					<div class="header">Error!!</div>
					<p>${errorMsg}</p>
				</div>
				
			</div>
			<c:remove var="errorMsg" scope="session" />
		</c:if>
		<c:if test="${not empty sessionScope.success }">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="content">
					<div class="header">Success!!</div>
					<p>Hotel recommendation is changed.</p>
				</div>
			</div>
			<c:remove var="success" scope="session"/>
		</c:if>
		<a class="ui gray icon button" href="${pageContext.request.contextPath}/recommend-room">
			<i class="angle left icon"></i>
			Hotel list
		</a>
		<h3>Recommending Rooms</h3>
		<c:if test="${empty requestScope.recommendingRooms }">
			<div>No entries</div>
		</c:if>
		<c:if test="${not empty requestScope.recommendingRooms}">
		<div class="ui vertical fluid menu">
			<c:forEach var="room" items="${requestScope.recommendingRooms}">
				<div class="item">
				<div class="ui green label">${room.roomType }</div>
				<div class="content">
					<h1 class="ui header">${room.roomNo }</h1>
					<form class="ui form" method="post" action="${pageContext.request.contextPath}/recommend-room/rooms/recommend?id=${room.roomId}">
						<div class="inline fields">
							<div class="field">
								<label>Recommend</label>
								<input name="recommend" type="number" min="0" value="${not empty room.recommended?room.recommended:''}"/>
							</div>
							<div class="field"><button class="ui blue submit button">Change</button></div>
						</div>
					</form>
				</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		<h3>Non-recommending Rooms</h3>
		<c:if test="${empty requestScope.nonRecommendingRooms}">
			<div>No entries</div>
		</c:if>
		<c:if test="${not empty requestScope.nonRecommendingRooms}">
		<div class="ui vertical fluid menu">
			<c:forEach var="room" items="${requestScope.nonRecommendingRooms}">
				<div class="item">
					<div class="ui green label">${room.roomType }</div>
					<div class="content">
						<h1 class="ui header">${room.roomNo }</h1>
						<form class="ui form" method="post" action="${pageContext.request.contextPath}/recommend-room/rooms/recommend?id=${room.roomId}">
							<div class="inline fields">
								<div class="field">
									<label>Recommend</label>
									<input name="recommend" type="number" min="0" value="${not empty room.recommended?room.recommended:''}"/>
								</div>
								<div class="field"><button class="ui blue submit button">Recommend</button></div>
							</div>
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
	</div>
</div>

</body>
</html>