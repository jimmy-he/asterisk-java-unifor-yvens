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
				<td class="title" colspan="7">Filas de Espera</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todos as filas de espera</td>
			</tr>
			<tr>
				<td class="header">TAG</td>
				<td class="header">Qtde. Comandos</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="queue" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${queue.tag}"></c:out>
					</td>
					<td class="result">
						<c:out value="${queue.commandListSize}"></c:out>
					</td>
					<td class="result">
						<a href="ListQueueCommandServlet?tag=${queue.tag}">Detalhar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="ListWaitQueueServlet?remover=${queue.tag}&id=${queue.id}">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />