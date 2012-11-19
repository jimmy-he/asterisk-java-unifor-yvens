<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>

<jsp:include page="../Includes/headerMenu.jsp" />

<div class="pageBody">
	<div class="pageTable">
		<table align="center" class="result" cellspacing="0" style="width: auto;">
			<tr>
				<td class="title" colspan="7">Rotas de Discagem - ${tag}</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todas as rotas de discagem do plano ${tag}</td>
			</tr>
			<tr>
				<td class="header">Identificador</td>
				<td class="header">Qtde. Comandos</td>
				<td class="header" style="border: none;" colspan="3">Opções</td>
			</tr>
			<c:forEach var="dialRoute" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${dialRoute.identifier}"></c:out>
					</td>
					<td class="result">
						<c:out value="${dialRoute.listCommandSize}"></c:out>
					</td>
					<td class="result">
						<a href="#">Detalhar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="#">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div style="text-align: center;">
			<form action="#" method="post">
				<input type="submit" value="Inserir Rota">
			</form>
		</div>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />