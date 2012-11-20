<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		igualarDiv();
	});

	function igualarDiv() {
		$("#columnOne").height($("#columnTwo").height());
	}

	function helpWindow() {
		var NWin = window.open('CommandHelpServlet', '', 'height=600,scrollbars=yes,width=800');
	    if (window.focus) {
	      NWin.focus();
	    }
	}
	
</script>
</head>

<jsp:include page="../Includes/headerMenu.jsp" />
<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft"></div>

	<div id="columnTwo" class="pageBodyRight">
		<h3>${tarefa}</h3>

		<!-- Atributos que normalmente são modificados quando criamos um comando de discagem -->

		<form method="post" action="CrudQueueCommandServlet?tag=${tag}&tarefa=${btnSubmit}&atividade=${atividade}">
			<input type="hidden" name="id" value="${id}">
			<table id="main_table" cellspacing="0">
				<tr>
					<td class="left">Ordem</td>
					<td class="right"><input name="order" type="text" value="${order}">
					</td>
				</tr>
				<tr>
					<td class="left">Campo</td>
					<td class="right"><input name="field" type="text" value="${field}">
					</td>
				</tr>
				<tr>
					<td class="left">Comando</td>
					<td class="right"><input name="command" type="text" value="${command}">
					</td>
				</tr>
			</table>
			<table cellspacing="0">
				<tr>
					<td class="buttons" colspan="2">
						<input type="submit" value="${btnSubmit}"> 
						<input type="reset" value="Limpar">
						<input type="button" value="Ajuda" onclick="helpWindow();">
					</td>
				</tr>
			</table>
		</form>
		
		<a href="ListDialCommandServlet?tag=${tag}">Voltar</a>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />