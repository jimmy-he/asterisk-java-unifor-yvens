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
				<td class="title" colspan="7">Tabela de Ramais SIP</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todos os ramais SIP do servidor</td>
			</tr>
			<tr>
				<td class="header">TAG</td>
				<td class="header">Caller ID</td>
				<td class="header">Username</td>
				<td class="header">Type</td>
				<td class="header">Context</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="ramal" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${ramal.tag}"></c:out>
					</td>
					<td class="result">
						<c:out value="${ramal.callerId}"></c:out>
					</td>
					<td class="result">
						<c:out value="${ramal.username}"></c:out>
					</td>
					<td class="result">
						<c:out value="${ramal.type}"></c:out>
					</td>
					<td class="result">
						<c:out value="${ramal.context}"></c:out>
					</td>
					<td class="result">
						<a href="#">Alterar</a>
					</td>
					<td class="result" style="border: none;">
						<a href="#">Remover</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />