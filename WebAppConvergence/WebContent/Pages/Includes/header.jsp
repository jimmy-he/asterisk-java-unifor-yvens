<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
</head>

<body>
<!-- Header default da aplicaÃ§Ã£o web -->
	<div id="pageHeader">
		<div class="logoHeader"> 
			<img alt="" src="Pages/Resources/logoAsteroid2.png" style="float: right;">
		</div>
	
		<div class="emptyWhiteSpace" style="padding-top: 40px;"> </div>
		
		<div class="greyLine"> </div>
		
		<div class="header">
			<div class="header_item"> 
				<a id="inicio" href="IndexServlet">
					Início
				</a>
			</div>
			<div class="header_item"> 
				<a id="servidor" href="#">
					Servidor
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="servidor_opcoes" href="ServidorOpcoesServlet"> Opções </a>
						</li>
						<li>
							Consectetur
						</li>
						<li>
							Adipiscing
						</li>
					</ul>
				</div>
			</div>
			<div class="header_item"> 
				<a id="Ipsum" href="#">
					Ipsum
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="Lorem" href="#"> Link </a>
						</li>
						<li>
							Consectetur
						</li>
						<li>
							Adipiscing
						</li>
					</ul>
				</div>
			</div>
			<div class="header_item"> 
				<a id="Dolor" href="#">
					Dolor
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="Lorem" href="#"> Link </a>
						</li>
						<li>
							Consectetur
						</li>
						<li>
							Adipiscing
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
