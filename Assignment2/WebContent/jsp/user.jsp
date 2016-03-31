<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - User</title>
<jsp:include page="include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close')
	  .on('click', function() {
	    $(this).closest('.message').transition('fade');
	  });
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
	
	$("form[name='personal']")
	.form({
		fields:{
			firstname:{
				rules:[
					{
						type:"empty",
						prompt: "You have to provide your first name!"
					}       
				]
			},
			lastname:{
				rules:[
					{
						type:"empty",
						prompt: "You have to provide your last name!"
					}       
				]
			}
		},
		inline: true
	})
	.on("submit",function(e){
		if($(this).form("is valid")){
			console.log("gonna update personal")
		}
	});
	
	$("form[name='account']")
	.form({
		fields:{
			oldPassword:{
				rules:[
					{
						type:"empty",
						prompt:"Please enter your old password."
					}       
				]
			},
			newPassword:{
				rules:[
					{
						type:"empty",
						prompt:"Please enter your new password."
					}       
				]
			},
			confirmPassword:{
				rules:[
					{
						type:"empty",
						prompt:"Please repeat your new password."
					},
					{
						type:"match[newPassword]",
						prompt:"The password does not match your new password."
					}
				]
			}
		},
		inline: true
	})
	.on("submit",function(e){
		if($(this).form("is valid")){
			console.log("gonna update account");
		}
	});
})
</script>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
<div class="ui attached segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="user icon"></i>
			<div class="content">
				User Information
				<div class="sub header">Update your information here</div>
			</div>
		</h1>
		<%-- <pre>${sessionScope }</pre> --%>
		<c:if test="${sessionScope.success}">
			<div class="ui success message">
				<i class="close icon"></i>
				<div class="header">Success</div>
				<p>Your information is updated.</p>
			</div>
			<c:remove var="success" scope="session"/>
		</c:if>
		<c:if test="${sessionScope.failure}">
			<div class="ui error message">
				<i class="close icon"></i>
				<div class="header">Failure</div>
				<p>${sessionScope.errorMsg }</p>
			</div>
			<c:remove var="failure" scope="session"/>
			<c:remove var="errorMsg" scope="session"/>
		</c:if>
		<div class="ui top attached tabular menu">
			<a class="active item" data-tab="personal">Personal</a>
			<a class="item" data-tab="account">Account</a>
		</div>
		<div class="ui bottom attached active tab segment" data-tab="personal">
			<form class="ui form" action="${pageContext.request.contextPath }/user/update/personal" method="post" name="personal">
				<div class="three fields">
					<div class="four wide field">
	  					<label>Title</label>
	  					<select class="ui dropdown" name="title">
	  						<option value="Mr" ${sessionScope.loginUser.title=="Mr" ? "selected" : ""} >Mr</option>
	  						<option value="Mrs" ${sessionScope.loginUser.title=="Mrs" ? "selected" : ""} >Mrs</option>
	  						<option value="Ms" ${sessionScope.loginUser.title=="Ms" ? "selected" : ""} >Ms</option>
	  					</select>
	  				</div>
					<div class="field">
						<label>First Name</label>
						<input type="text" name="firstname" value="${sessionScope.loginUser.firstName}"/>
					</div>
					<div class="field">
						<label>Last Name</label>
						<input type="text" name="lastname" value="${sessionScope.loginUser.lastName}"/>
					</div>
				</div>
				<div class="four wide field">
					<label>Gender</label>
	  				<select class="ui dropdown" name="gender">
	  					<option value="F" ${sessionScope.loginUser.gender=="F" ? "selected" : ""} >Female</option>
	  					<option value="M" ${sessionScope.loginUser.gender=="M" ? "selected" : ""} >Male</option>
	  				</select>
				</div>
				
				<button class="ui blue submit button">Update</button>
				<button type="reset" class="ui reset button">Reset</button>
				<div class="ui error message"></div>
			</form>	
		</div>
		<div class="ui bottom attached tab segment" data-tab="account">
			<form class="ui form" method="post" name="account" action="${pageContext.request.contextPath }/user/update/account">
				<div class="eight wide field">
					<label>Email (Read-only)</label>
					<input type="text" name="email" value="${sessionScope.loginUser.email}" disabled/>
				</div>
				<div class="eight wide field">
					<label>Old Password</label>
					<input type="password" name="oldPassword"/>
				</div>
				<div class="eight wide field">
					<label>New Password</label>
					<input type="password" name="newPassword"/>
				</div>
				<div class="eight wide field">
					<label>Confirm Password</label>
					<input type="password" name="confirmPassword"/>
				</div>
				
				<button class="ui blue submit button">Update</button>
				<button type="reset" class="ui reset button">Reset</button>
				<div class="ui error message"></div>
			</form>	
		</div>
	</div>
</div>

</body>
</html>