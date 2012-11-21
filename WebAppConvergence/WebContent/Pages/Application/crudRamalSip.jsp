<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {

		$("#hiddenAttr").hide();

		igualarDiv();

		//carregar o valor dos radio bottons
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
			action="CrudRamalSipServlet?tarefa=${btnSubmit}&atividade=${atividade}">
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
					<td class="left">Username</td>
					<td class="right"><input name="username" type="text"
						value="${username}"></td>
				</tr>
				<tr>
					<td class="left">Secret</td>
					<td class="right"><input name="secret" type="password"
						value="${secret}"></td>
				</tr>
				<tr>
					<td class="left">Ramal Type</td>
					<td class="right"><select size="1" name="type"
						style="width: 65%">
							<option selected value="...">Selecione</option>
							<option value="FRIEND">FRIEND</option>
					</select></td>
				</tr>
			</table>

			<!-- Atributos avançados da criação de um ramal -->
			<!-- RamalType type, boolean canReinvite, String context, String dtmfMode, int callLimit, boolean nat -->
			<!-- Valores default para os atributos do ramal -->
			<!-- RamalType.FRIEND, false, "LOCAL", "rfc2833", 2, false); -->
			<div id="hiddenAttr">
				<table cellspacing="0">
					<tr class="hidden_tr">
						<td class="left">Can Reinvite</td>
						<c:if test="${empty canReinvite}">
							<td class="right"><input name="canReinvite" type="radio"
								value="true"> Yes <input name="canReinvite"
								checked="checked" type="radio" value="false"> No</td>
						</c:if>
						<c:if test="${!empty canReinvite}">
							<c:if test="${canReinvite == 'yes'}">
								<td class="right"><input name="canReinvite" type="radio"
									value="true" checked="checked"> Yes <input
									name="canReinvite" type="radio" value="false"> No</td>
							</c:if>
							<c:if test="${canReinvite != 'yes'}">
								<td class="right"><input name="canReinvite" type="radio"
								value="true"> Yes <input name="canReinvite"
								checked="checked" type="radio" value="false"> No</td>
							</c:if>
						</c:if>
					</tr>
					<tr class="hidden_tr">
						<td class="left">Context</td>
						<td class="right"><select name="context">
								<c:forEach var="dialPlan" items="${dialPlanList}">
									<option>${dialPlan.tag}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="hidden_tr">
						<td class="left">DtmfMode</td>
						<td class="right"><input name="dtmfMode" type="text"
							value="${dtmfMode}"></td>
					</tr>
					<tr class="hidden_tr">
						<td class="left">CallLimit</td>
						<td class="right"><input name="callLimit" type="text"
							value="${callLimit}"></td>
					</tr>
					<tr class="hidden_tr">
						<td class="left">Account Code</td>
						<td class="right"><input name="accountCode" type="text"
							value="${accountCode}"></td>
					</tr>
					<tr class="hidden_tr">
						<td class="left">Host</td>
						<td class="right"><input name="host" type="text"
							value="${host}"></td>
					</tr>
					<tr class="hidden_tr">
						<td class="left">Nat</td>
						<c:if test="${empty nat}">
							<td class="right"><input name="nat" type="radio"
								value="true"> Yes <input name="nat" checked="checked"
								type="radio" value="false"> No</td>
						</c:if>
						<c:if test="${!empty nat}">
							<c:if test="${nat == 'yes'}">
								<td class="right"><input name="nat" type="radio"
									value="true" checked="checked"> Yes <input name="nat"
									type="radio" value="false"> No</td>
							</c:if>
							<c:if test="${nat != 'yes'}">
								<td class="right"><input name="nat" type="radio"
									value="true"> Yes <input name="nat" checked="checked"
									type="radio" value="false"> No</td>
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