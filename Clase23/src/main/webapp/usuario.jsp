<%@page import="com.digitalers.enums.MessageEnum"%>
<%@page import="com.digitalers.controllers.LoginController"%>
<%@page import="com.digitalers.commons.time.DateUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.digitalers.entities.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="static/css/form.css">
<link rel="stylesheet" href="static/css/button.css">
<link rel="stylesheet" href="static/css/main.css">
<title>ABM - Usuarios</title>
</head>
<body>
	<%
	User user = (User) session.getAttribute("user");
	
	User userEdit = (User) session.getAttribute("userEdit");
	String id = (userEdit != null) ? String.valueOf(userEdit.getId()) : "";
	String email = (userEdit != null) ? userEdit.getEmail() : "";
	boolean activo = (userEdit != null) ? userEdit.isActive() : true; 

	%>

	<header>
		<h1>Proyecto de Usuarios</h1>
		<%
		if (user == null) {
			response.sendRedirect("index.jsp");
		} else {
		%>
		<h2><%=(user.getEmail() + " - " + session.getId())%></h2>

	</header>
	<nav>
		<a href="lista.jsp">Lista de Usuarios</a> <a style="float: right;"
			href="login?invalidate=true">Cerrar Sesion <%=user.getEmail().substring(0, user.getEmail().indexOf("@"))%>
		</a>
	</nav>

	<%
	MessageEnum messageEnum = (MessageEnum) request.getAttribute("messageEnum");

	if (messageEnum != null) {
	%>
	<h2 style="color:<%=messageEnum.getColor()%>;"><%=messageEnum.getMessage()%></h2>
	<%
	}
	%>

	<%
	}
	%>
	<section class="body">
		<form id="form-1" method="post" action="users">
			
			<input type="hidden" name="id"
				value = "<%=id%>" readonly /> 
		
			<label for="email">Correo:</label> 
			<input type="email" id="email"
				name="email" required  
				value = "<%=email%>" 
				<%if(!email.equals("")){ %> readonly <%} %>
				placeholder="tuusuario@dominio.ext" /> 
				
			<label for="key1">Clave:</label> 
			<input type="password" id="key1"
				name="key1" required placeholder="Clave de Acceso"> 
			
			<label for="key2">Confirma Clave:</label> 
			<input type="password" id="key2"
				name="key2" required placeholder="Confirma tu clave"> 
				
			<select id="active" name="active">
				<option value="true" <% if(activo) { %> selected <% } %> >Activo</option>
				<option value="false" <% if(!activo) { %> selected <% } %> >Inactivo</option>
			</select>
			
			<div>
				<button class="success" type="submit">Enviar</button>
				<button class="warning" type="reset">Limpiar</button>
			</div>
			
		</form>
	</section>

	<footer>
		<p>Todos los derechos reservados Educacion IT - Digitalers Telecom</p>
	</footer>


</body>
</html>