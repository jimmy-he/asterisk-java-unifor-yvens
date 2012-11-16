<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		igualarTableWidth();
	});

	function igualarTableWidth() {

		$("#tableIAX").width($("#tableSip").width());

	}
</script>
</head>

<jsp:include page="../Includes/headerMenu.jsp" />

<div class="pageBody">
	<div class="pageTable">
		<h4 style="text-align: center;">Monitoramento de Ramais</h4>

		<div style="text-align: center;">
			<form action="RamalMonitorServlet" method="post">
				<input type="hidden" name="update" value="Ramais atualizados"> <input
					type="submit" value="Atualizar">
			</form>

		</div>

		<table id="tableSip" align="center" class="result" cellspacing="0"
			style="width: auto;">
			<tr>
				<td class="title" colspan="7">Ramais SIP</td>
			</tr>
			<tr>
				<td class="header">Name/Username</td>
				<td class="header">Host</td>
				<td class="header">Dyn</td>
				<td class="header">Forceport</td>
				<td class="header">ACL</td>
				<td class="header">Port</td>
				<td class="header" style="border: none;">Status</td>
			</tr>
			<c:forEach var="ramal" items="${listSip}">
				<tr>
					<td class="result"><c:out value="${ramal.name}"></c:out></td>
					<td class="result"><c:out value="${ramal.host}"></c:out></td>
					<td class="result"><c:out value="${ramal.dyn}"></c:out></td>
					<td class="result"><c:out value="${ramal.forceport}"></c:out>
					</td>
					<td class="result"><c:out value="${ramal.acl}"></c:out></td>
					<td class="result"><c:out value="${ramal.port}"></c:out></td>
					<td class="result"><c:out value="${ramal.status}"></c:out></td>
				</tr>
			</c:forEach>
		</table>

		<table id="tableIAX" align="center" class="result" cellspacing="0"
			style="width: auto;">
			<tr>
				<td class="title" colspan="6">Ramais IAX</td>
			</tr>
			<tr>
				<td class="header">Name/Username</td>
				<td class="header">Host</td>
				<td class="header">Dyn</td>
				<td class="header">Mask</td>
				<td class="header">Port</td>
				<td class="header" style="border: none;">Status</td>
			</tr>
			<c:forEach var="ramal" items="${listIAX}">
				<tr>
					<td class="result"><c:out value="${ramal.name}"></c:out></td>
					<td class="result"><c:out value="${ramal.host}"></c:out></td>
					<td class="result"><c:out value="${ramal.dyn}"></c:out></td>
					<td class="result"><c:out value="${ramal.mask}"></c:out></td>
					<td class="result"><c:out value="${ramal.port}"></c:out></td>
					<td class="result"><c:out value="${ramal.status}"></c:out></td>
				</tr>
			</c:forEach>
		</table>

		<div style="text-align: center;">
			<form action="RamalMonitorServlet" method="post">
				<input type="hidden" name="update" value="Ramais atualizados"> <input
					type="submit" value="Atualizar">
			</form>
		</div>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />
