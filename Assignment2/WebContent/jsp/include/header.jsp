<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="enums.Role" %>
<%-- HEADER --%>
<div class="ui attached icon red message">
	<i class="warning icon"></i>
	<div class="content">
		<div class="header">
			IMPORTANT
		</div>
		<p>This web site exists to fulfill the coursework requirement of CS4280. Do not use your real personal data as input. </p>	
	</div>
</div>
<div class="ui attached menu">
	<a href="${pageContext.request.contextPath}/" class="header item">
		Hotel Pro
	</a>
	<c:if test="true">
		<a href="${pageContext.request.contextPath}/booking" class="item">
			Booking
		</a>
	</c:if>
	<c:if test="${not empty sessionScope.loginUser && sessionScope.loginUser.role != Role.Customer }">
	<div class="ui simple dropdown item">
		Manage
		<i class="dropdown icon"></i>
		<div class="menu">
			<c:if test="${sessionScope.loginUser.role == Role.Manager}">
				<a href="${pageContext.request.contextPath}/recommend-room" class="item">Recommend Featured Hotels</a>
			</c:if>
			<c:if test="${sessionScope.loginUser.role == Role.Manager}">
				<a href="#" class="item">Hotels</a>
			</c:if>
			<c:if test="${sessionScope.loginUser.role == Role.Chief_Manager}">
				<a href="${pageContext.request.contextPath}/manage-user" class="item">Hotel Managers</a>
			</c:if>
		</div>
	</div>
	</c:if>
	<div class="right menu">
		<c:if test="${not empty sessionScope.loginUser}">
			<a href="${pageContext.request.contextPath}/user" class="item" id="userItem">
				<i class="user icon"></i>
				<span>${sessionScope.loginUser.firstName} ${sessionScope.loginUser.lastName}</span>
			</a>
		</c:if>
		<c:if test="${not empty sessionScope.loginUser}">
			<a href="${pageContext.request.contextPath}/auth/logout" class="item" id="logoutItem">
				Logout
			</a>
		</c:if>
		<c:if test="${empty sessionScope.loginUser}">
			<a class="item" id="btnLogin">
				Login
			</a>
		</c:if>
	</div>
</div>