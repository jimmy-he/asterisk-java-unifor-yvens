<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asteroid 0.1 - Ajuda</title>

<link rel="stylesheet" href="Pages/Styles/style.css" type="text/css">
<script type="text/javascript" src="Pages/Libs/jquery.js"></script>
<script type="text/javascript" src="Pages/Libs/jquery-ui-1.9.1.custom.js"></script>
</head>
<body>

<div id="pageHeader">
	<div class="emptyWhiteSpace" style="padding-top: 40px;"> </div>
	
	<div class="greyLine"> </div>		
</div>
<div class="header">
</div>

<div style="padding-left: 10%">
	<h4>Lista de Comandos para Planos de Discagem</h4>
</div>
		
<div class="helpList">
	<ul>
		<c:forEach items="${list}" var="help">
		<li>${help}</li> 
		</c:forEach>
		<li>Teste</li> 
	</ul>
</div>

<div style="text-align: center;">
		<input type="button" value="Fechar" onclick="self.close();">
</div>

<div class="footer"></div>
</body>
</html>