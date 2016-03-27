<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
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
	<div class="header item">
		Hotel Go
	</div>
	<c:if test="true">
		<div class="item">
			Booking
		</div>
	</c:if>
	<c:if test="${scopeSession.loginUser.isManager}">
		<div class="item">
			Manage
		</div>
	</c:if>
	<div class="right menu">
		<c:if test="${scopeSession.loginUser!=null}">
			<a href="#" class="item" id="userItem">
				<i class="user icon"></i>
				<span>${scopeSession.loginUser.username}</span>
			</a>
		</c:if>
		<c:if test="${scopeSession.loginUser!=null}">
			<a href="${pageContext.request.contextPath}/auth/logout" class="item" id="logoutItem">
				Logout
			</a>
		</c:if>
		<c:if test="${scopeSession.loginUser==null}">
			<a href="${pageContext.request.contextPath}/auth/login" class="item" id="loginItem">
				Login
			</a>
		</c:if>
	</div>
</div>