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

</script>
</head>

<jsp:include page="../Includes/headerMenu.jsp" />
<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft"></div>

	<div id="columnTwo" class="pageBodyRight">
		<h3>${tarefa}</h3>

		<!-- Atributos que normalmente são modificados quando criamos um plano de discagem -->

		<form method="post" action="CrudDialRouteServlet?tarefa=${btnSubmit}&atividade=${atividade}">
			<table id="main_table" cellspacing="0">
				<tr>
					<td class="left">Identificador</td>
					<td class="right"><input name="identifier" type="text" value="${identifier}">
					</td>
				</tr>
			</table>
			<table cellspacing="0">
				<tr>
					<td class="buttons" colspan="2">
						<input type="submit" value="${btnSubmit}"> 
						<input type="reset" value="Limpar">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />