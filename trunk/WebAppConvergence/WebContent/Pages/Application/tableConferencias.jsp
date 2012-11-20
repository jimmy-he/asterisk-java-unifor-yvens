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
				<td class="title" colspan="7">Tabela de Salas de Conferência</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="7">Listagem de todas as salas de conferência do servidor</td>
			</tr>
			<tr>
				<td class="header">Number</td>
				<td class="header">Context</td>
				<td class="header">AnnounceUserCount</td>
				<td class="header">MusicOnHold</td>
				<td class="header">QuietMode</td>
				<td class="header" style="border: none;" colspan="2">Opções</td>
			</tr>
			<c:forEach var="conference" items="${list}" >
				<tr>
					<td class="result">
						<c:out value="${conference.number}"></c:out>
					</td>
					<td class="result">
						<c:out value="${conference.context}"></c:out>
					</td>
					<td class="result">
						<c:out value="${conference.announceUserCount}"></c:out>
					</td>
					<td class="result">
						<c:out value="${conference.musicOnHold}"></c:out>
					</td>
					<td class="result">
						<c:out value="${conference.quietMode}"></c:out>
					</td>
					<td class="result">
						<a href="CrudConferenceRoomServlet?tarefa=alteracao&tag=${conference.number}">Alterar</a>
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