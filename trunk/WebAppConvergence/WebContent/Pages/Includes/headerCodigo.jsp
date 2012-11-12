<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Asteroid 0.1</title>
	
	<link rel="stylesheet" href="Pages/Styles/style.css" type="text/css">
	<script type="text/javascript" src="Pages/Libs/jquery.js"></script>
	<script type="text/javascript" src="Pages/Libs/jquery-ui-1.9.1.custom.js"></script>
	
	<script type="text/javascript">
		function openSubMenu()
		{
			$(this).find('.header_menu').css('visibility', 'visible');
		}
		
		function closeSubMenu()
		{
			$(this).find('.header_menu').css('visibility', 'hidden');
		}
		
		$(document).ready(function () {
			$('.header_item').bind('mouseover', openSubMenu);
			$('.header_item').bind('mouseout',  closeSubMenu);
		});
	</script>


