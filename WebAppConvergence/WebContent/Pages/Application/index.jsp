<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="../Includes/header.jsp" />

<script type="text/javascript">
	$(document).ready(function () {
		if($("#columnTwo").height() > $("#columnOne").height())
		{
			$("#columnOne").height($("#columnTwo").height());
		}
		else
		{
			$("#columnTwo").height($("#columnOne").height());
		}
	});
</script>

<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft">
	
	</div>
	<div id="columnTwo" class="pageBodyRight">
		Bem-vindo ao Asteroid 0.1.<br/>
		Esse website funciona como um client para o servidor asterisk 
		<h1>${servidor}</h1>
		Para verificar o status do servidor vá na aba <b>Servidor</b> e selecione <b>Opções</b>.
	</div>
</div>

<jsp:include page="../Includes/footer.html" />  
