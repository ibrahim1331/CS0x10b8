<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<jsp:useBean id="customer1" class="model.Customer" scope="request"/>
	<%-- <jsp:setProperty name="customer1" property="firstName" value="aaa"/> --%>
	<head>
		<title>Login</title>
		
	</head>
	<body>
		<h1>Login</h1>
		<form action="login" method="post" name="form">
			<input type="hidden" name="action" value="login"/>
			<p>Email: <input type="text" name="email" /></p>
			<p>Password: <input type="password" name="password"/></p>
			<input type="submit" value="Login"/>
		</form>
		<div>
			<p>Customer First Name: <jsp:getProperty name="customer1" property="firstName"/></p>
		</div>
	</body>
</html>