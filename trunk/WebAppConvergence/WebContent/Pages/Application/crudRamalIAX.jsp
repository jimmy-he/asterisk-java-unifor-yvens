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

		<!-- Atributos que normalmente são modificados quando criamos um ramal -->
		<!-- String tag, String callerId, String username, String secret -->

		<form method="post"
			action="CrudRamalIAXServlet?tarefa=${btnSubmit}&atividade=${atividade}">
			<table id="main_table" cellspacing="0">
				<tr>
					<td class="left">Tag</td>
					<td class="right"><input name="tag" type="text" value="${tag}">
					</td>
				</tr>
				<tr>
					<td class="left">Caller Id</td>
					<td class="right"><input name="callerId" type="text"
						value="${callerId}"></td>
				</tr>
				<tr>
					<td class="left">Default User</td>
					<td class="right"><input name="defaultUser" type="text"
						value="${defaultUser}"></td>
				</tr>
				<tr>
					<td class="left">Secret</td>
					<td class="right"><input name="secret" type="password"
						value="${secret}"></td>
				</tr>
				<tr>
					<td class="left">Context</td>
					<td class="right"><select name="context">
							<c:forEach var="dialPlan" items="${dialPlanList}">
								<option>${dialPlan.tag}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>

			<!-- Atributos avançados da criação de um ramal -->
			<!-- RamalType type, boolean transfer, String context, String auth, boolean requireCallToken -->
			<!-- Valores default para os atributos do ramal -->
			<!-- RamalType.FRIEND, true, "LOCAL", "md5", false); -->
			<div id="hiddenAttr">
				<table cellspacing="0">

					<tr class="hidden_tr">
						<td class="left">Transfer</td>
						<c:if test="${empty transfer}">
							<td class="right"><input name="transfer" type="radio"
								value="true" checked="checked"> Yes <input
								name="transfer" type="radio" value="false"> No</td>
						</c:if>
						<c:if test="${!empty transfer}">
							<c:if test="${transfer == 'yes'}">
								<td class="right"><input name="transfer" type="radio"
									value="true" checked="checked"> Yes <input
									name="transfer" type="radio" value="false"> No</td>
							</c:if>
							<c:if test="${transfer != 'yes'}">
								<td class="right"><input name="transfer" type="radio"
									value="true"> Yes <input name="transfer" type="radio"
									value="false" checked="checked"> No</td>
							</c:if>
						</c:if>
					</tr>
					<tr>
						<td class="left">Ramal Type</td>
						<td class="right"><select size="1" name="type"
							style="width: 65%">

								<option value="FRIEND">FRIEND</option>
						</select></td>
					</tr>

					<tr class="hidden_tr">
						<td class="left">Auth</td>
						<td class="right"><input name="auth" type="text"
							value="${auth}"></td>
					</tr>

					<tr class="hidden_tr">
						<td class="left">Host</td>
						<td class="right"><input name="host" type="text"
							value="${host}"></td>
					</tr>

					<tr class="hidden_tr">
						<td class="left">Require Call Token</td>
						<c:if test="${empty requireCallToken}">
							<td class="right"><input name="requireCallToken"
								type="radio" value="true"> Yes <input
								name="requireCallToken" checked="checked" type="radio"
								value="false"> No</td>
						</c:if>
						<c:if test="${!empty requireCallToken}">
							<c:if test="${requireCallToken == 'yes'}">
								<td class="right"><input name="requireCallToken"
									type="radio" value="true" checked="checked"> Yes <input
									name="requireCallToken" type="radio" value="false"> No</td>
							</c:if>
							<c:if test="${requireCallToken != 'yes'}">
								<td class="right"><input name="requireCallToken"
									type="radio" value="true"> Yes <input
									name="requireCallToken" checked="checked" type="radio"
									value="false"> No</td>
							</c:if>
						</c:if>
					</tr>
				</table>
			</div>

			<table cellspacing="0">
				<tr>
					<td class="buttons" colspan="2"><input type="submit"
						value="${btnSubmit}"> <input type="reset" value="Limpar">
						<input type="button" value="Avançado" onclick="avancado();">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />