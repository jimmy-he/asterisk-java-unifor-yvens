<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {

		$("#hiddenAttr").hide();

		igualarDiv();
	});

	function igualarDiv() {

		$("#columnOne").height($("#columnTwo").height());

	}

	function avancado() {

		$("#hiddenAttr").slideToggle("slow", function() {
			igualarDiv();
		});

	}
</script>
</head>

<jsp:include page="../Includes/headerMenu.jsp" />
<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft"></div>

	<div id="columnTwo" class="pageBodyRight">
		<h3>${tarefa}</h3>

		<!-- Atributos que normalmente são modificados quando criamos um agent -->
		<!-- String code, String name, String secret -->

		<form method="post" action="CrudAgentServlet?tarefa=${btnSubmit}&atividade=${atividade}">
			<input type="hidden" name="id" value="${id}">
			<table id="main_table" cellspacing="0">
				<tr>
					<td class="left">Código</td>
					<td class="right"><input name="code" type="text" value="${code}">
					</td>
				</tr>
				<tr>
					<td class="left">Nome</td>
					<td class="right"><input name="name" type="text" value="${name}">
					</td>
				</tr>
				<tr>
					<td class="left">Secret</td>
					<td class="right"><input name="secret" type="password" value="${secret}">
					</td>
				</tr>
			</table>

			<table cellspacing="0">
				<tr>
					<td class="buttons" colspan="2"><input type="submit"
						value="${btnSubmit}"> <input type="reset" value="Limpar">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />