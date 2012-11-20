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
		
		<!-- Ajeitar -->
		<div style="padding-left: 10%;">
			<a href="ListWaitQueueServlet">Voltar</a>
		</div>
	
		<table align="center" class="result" cellspacing="0" style="width: auto;">
			<tr>
				<td class="title" colspan="7">Comando da Fila - ${tag}</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todos os comandos da filas de espera</td>
			</tr>
			<tr>
				<td class="header">Ordem</td>
				<td class="header">Campo</td>
				<td class="header">Valor</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="command" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${command.order}"></c:out>
					</td>
					<td class="result">
						<c:out value="${command.field}"></c:out>
					</td>
					<td class="result">
						<c:out value="${command.command}"></c:out>
					</td>
					<td class="result">
						<a href="CrudQueueCommandServlet?tarefa=alteracao&atividade=alteracao&tag=${tag}&id=${command.id}">Alterar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="CrudQueueCommandServlet?tarefa=remocao&atividade=remocao&tag=${tag}&id=${command.id}">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div style="text-align: center;">
			<form action="CrudQueueCommandServlet?tag=${tag}" method="post">
				<input type="submit" value="Inserir Comando">
			</form>
		</div>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />