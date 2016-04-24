<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go</title>
<jsp:include page="include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	//init dropdown
	$(".ui.dropdown").dropdown();
	
	//init tab in login/register modal
	$("#modalLoginReg .menu .item").tab();
	
})
</script>
</head>
<body>
<jsp:include page="include/header.jsp"></jsp:include>
<div class="ui basic segment">
	<h1 class="ui centered header">Hotel Pro. The most advanced hotel booking website.</h1>
		<div class="ui container">
			<form class="ui form" action="search" method="post">
				<div class="ui two column centered grid">
					<div class="centered row">
						<div class="column">
							<div class="ui fluid action input">
								<input type="text" name="city" placeholder="Search..." required/>
								<button type="submit" class="ui button" >Search</button>	
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
</div>

<%-- ui modal for login/register --%>
<div class="ui modal" id="modalLoginReg">
  <div class="content">
  	<div class="ui top attached tabular menu">
  		<a class="item active" data-tab="login">Login</a>
  		<a class="item" data-tab="register">Register</a>
  	</div>
  	<div class="ui bottom attached active tab login segment" data-tab="login">
  		<form class="ui form" method="post" name="login">
  			<div class="field">
  				<label>Email</label>
  				<input type="text" placeholder="Email" name="email"/>
  			</div>
  			<div class="field">
  				<label>Password</label>
  				<input type="password" name="password"/>
  			</div>
  			<button class="ui green button" type="submit">Login</button>
  			<button class="ui reset button">Reset</button>
  			<div class="ui error message"></div>
  		</form>
  	</div>
  	<div class="ui bottom attached tab register segment" data-tab="register">
  		<form class="ui form" method="post" name="register">
  			<div class="eight wide field">
  				<label>Email</label>
  				<input type="text" placeholder="Email" name="email"/>
  			</div>
  			<div class="eight wide field">
  				<label>Password</label>
  				<input type="password" name="password"/>
  			</div>
  			<div class="four wide field">
  				<label>Gender</label>
  				<select class="ui dropdown" name="gender">
  					<option></option>
  					<option value="F">Female</option>
  					<option value="M">Male</option>
  				</select>
  			</div>
  			<div class="fields">
  				<div class="four wide field">
  					<label>Title</label>
  					<select class="ui dropdown" name="title">
  						<option></option>
  						<option value="Mr">Mr</option>
  						<option value="Mrs">Mrs</option>
  						<option value="Ms">Ms</option>
  					</select>
  				</div>
	  			<div class="six wide field">
	  				<label>First Name</label>
	  				<input type="text" placeholder="First Name" name="firstname"/>
	  			</div>
	  			<div class="six wide field">
	  				<label>Last Name</label>
	  				<input type="text" placeholder="Last Name" name="lastname"/>
	  			</div>	
  			</div>
  			<div class="inline field">
  				<div class="ui checkbox">
  					<input type="checkbox" name="terms" value="terms"/>
  					<label>Terms and Agreement</label>
  				</div>
  			</div>
  			<button class="ui green button" type="submit">Register</button>
  			<button class="ui reset button">Reset</button>
  			<div class="ui error message"></div>
  		</form>
  	</div>
  </div>
</div>
</body>
</html>