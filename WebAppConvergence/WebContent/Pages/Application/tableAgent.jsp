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
				<td class="title" colspan="7">Agentes</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todos os agentes</td>
			</tr>
			<tr>
				<td class="header">Código</td>
				<td class="header">Nome</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="agent" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${agent.code}"></c:out>
					</td>
					<td class="result">
						<c:out value="${agent.name}"></c:out>
					</td>
					<td class="result">
						<a href="CrudAgentServlet?atividade=alteracao&id=${agent.code}">Alterar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="CrudAgentServlet?atividade=remocao&id=${agent.code}">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />