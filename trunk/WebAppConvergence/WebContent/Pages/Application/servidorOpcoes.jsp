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
	
	function limpar()
	{
		var input = document.getElementById("ipServidor");
		input.setAttribute("value", "");
	}
</script>

<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft">
	
	</div>
	<div id="columnTwo" class="pageBodyRight">
		<h3>Informações do Servidor</h3>
		
		<form action="ServidorOpcoesServlet?form=true" method="post">
			<table cellspacing="0">
				<tr> 
					<td class="left">
						Ip do Servidor
					</td>
					<td class="right">
						<input id="ipServidor" name="ipServidor" type="text" value="${servidor}"> 
					</td>
				</tr>
				<tr> 
					<td class="buttons" colspan="2">
						<input type="submit" value="Enviar">
						<input type="button" value="Limpar" onclick="limpar();">
					</td>
				</tr>
			</table>	
		</form>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />  