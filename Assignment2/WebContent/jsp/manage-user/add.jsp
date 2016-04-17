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
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
	
	$("form[name='add']")
	.form({
		fields:{
			email: 'email',
			password: 'empty',
			title: 'empty',
			gender: 'empty',
			firstName: 'empty',
			lastName: 'empty',
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
			<i class="add user icon"></i>
			<div class="content">
				Add new Hotel Manager
			</div>
		</h1>
		<c:if test="${not empty sessionScope.errorMsg}">
			<div class="ui error message">
				${sessionScope.errorMsg }
			</div>
		</c:if>
		<form class="ui form" method="post" name="add" action="${pageContext.request.contextPath}/manage-user/add?add=1">
  			<div class="eight wide field">
  				<label>Email</label>
 				<c:if test="${not empty sessionScope.inputBefore }">
  					<input type="text" placeholder="Email" name="email" value="${sessionScope.inputBefore.email}"/>
  				</c:if>
  				<c:if test="${empty sessionScope.inputBefore }">
  					<input type="text" placeholder="Email" name="email"/>
  				</c:if>
  			</div>
  			<div class="eight wide field">
  				<label>Password</label>
  				<c:if test="${not empty sessionScope.inputBefore }">
  					<input type="password" name="password" value="${sessionScope.inputBefore.password }"/>
  				</c:if>
  				<c:if test="${empty sessionScope.inputBefore }">
  					<input type="password" name="password"/>
  				</c:if>
  			</div>
  			<div class="four wide field">
  				<label>Gender</label>
  				<select class="ui dropdown" name="gender">
  					<option></option>
  					<option value="F" ${not empty sessionScope.inputBefore.gender && sessionScope.inputBefore.gender eq "F"?"selected":""}>Female</option>
  					<option value="M" ${not empty sessionScope.inputBefore.gender && sessionScope.inputBefore.gender eq "M"?"selected":""}>Male</option>
  				</select>
  			</div>
  			<div class="fields">
  				<div class="four wide field">
  					<label>Title</label>
  					<select class="ui dropdown" name="title">
  						<option></option>
  						<option value="Mr" ${not empty sessionScope.inputBefore.title && sessionScope.inputBefore.title eq "Mr"?"selected":""}>Mr</option>
  						<option value="Mrs" ${not empty sessionScope.inputBefore.title && sessionScope.inputBefore.title eq "Mrs"?"selected":""}>Mrs</option>
  						<option value="Ms" ${not empty sessionScope.inputBefore.title && sessionScope.inputBefore.title eq "Ms"?"selected":""}>Ms</option>
  					</select>
  				</div>
	  			<div class="six wide field">
	  				<label>First Name</label>
	  				<c:if test="${not empty sessionScope.inputBefore }">
	  					<input type="text" placeholder="First Name" name="firstName" value="${sessionScope.inputBefore.firstName }"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore }">
	  					<input type="text" placeholder="First Name" name="firstName"/>
	  				</c:if>
	  			</div>
	  			<div class="six wide field">
	  				<label>Last Name</label>
	  				<c:if test="${not empty sessionScope.inputBefore }">
	  					<input type="text" placeholder="Last Name" name="lastName" value="${sessionScope.inputBefore.lastName }"/>
	  				</c:if>
	  				<c:if test="${empty sessionScope.inputBefore }">
	  					<input type="text" placeholder="Last Name" name="lastName"/>
	  				</c:if>
	  				
	  			</div>	
  			</div>
  			<button class="ui green button" type="submit">Register</button>
  			<button class="ui reset button">Reset</button>
  			<div class="ui error message"></div>
  		</form>
  		<c:remove var="errorMsg" scope="session"/>
  		<c:remove var="inputBefore" scope="session"/>
	</div>
</div>

</body>
</html>