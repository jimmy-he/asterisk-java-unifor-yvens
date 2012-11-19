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
				<td class="title" colspan="7">Comando de Discagem - ${tag}/${identifier}</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todos os comandos da rota de discagem</td>
			</tr>
			<tr>
				<td class="header">Ordem</td>
				<td class="header">Comando</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="dialCommand" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${dialCommand.order}"></c:out>
					</td>
					<td class="result">
						<c:out value="${dialCommand.command}"></c:out>
					</td>
					<td class="result">
						<a href="CrudDialCommandServlet?tag=${tag}&identifier=${identifier}&atividade=alteracao&id=${dialCommand.id}">Alterar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="CrudDialCommandServlet?tag=${tag}&identifier=${identifier}&atividade=remocao&id=${dialCommand.id}">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div style="text-align: center;">
			<form action="CrudDialCommandServlet?tag=${tag}&identifier=${identifier}" method="post">
				<input type="submit" value="Inserir Comando">
			</form>
		</div>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />