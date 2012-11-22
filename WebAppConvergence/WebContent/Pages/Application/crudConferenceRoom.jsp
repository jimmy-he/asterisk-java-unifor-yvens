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

		<!-- Atributos que normalmente são modificados quando criamos uma conferência -->
		<!-- String number, String context -->

		<form method="post"
			action="CrudConferenceRoomServlet?tarefa=${btnSubmit}&atividade=${atividade}">
			<table id="main_table" cellspacing="0">
				<tr>
					<td class="left">Number</td>
					<td class="right"><input name="number" type="text"
						value="${number}"><input name="oldNumber" type="hidden"
						value="${number}"></td>
				</tr>
				<tr>

					<td class="left">Context</td>
					<td class="right"><select name="context">
							<c:forEach var="dialPlan" items="${dialPlanList}">
								<option>${dialPlan.tag}</option>
							</c:forEach>
					</select></td>

				</tr>
				<tr>
					<td class="left">Password</td>
					<td class="right"><input name="password" type="password"
						value="${password}"></td>
				</tr>
			</table>

			<!-- Atributos avançados da criação de uma conferência -->
			<!-- boolean announceUserCount, boolean musicOnHold, boolean quietMode -->
			<!-- Valores default para os atributos da conferência -->
			<!-- true, true, false); -->
			<div id="hiddenAttr">
				<table cellspacing="0">

					<tr class="hidden_tr">
						<td class="left">Announce User Count</td>
						<td class="right"><input name="announceUserCount"
							type="radio" value="true" checked="checked"> Yes <input
							name="announceUserCount" type="radio" value="false"> No</td>
					</tr>

					<tr class="hidden_tr">
						<td class="left">Music on Hold</td>
						<td class="right"><input name="musicOnHold" type="radio"
							value="true" checked="checked"> Yes <input
							name="musicOnHold" type="radio" value="false"> No</td>
					</tr>

					<tr class="hidden_tr">
						<td class="left">Quiet Mode</td>
						<td class="right"><input name="quietMode" type="radio"
							value="true"> Yes <input name="quietMode"
							checked="checked" type="radio" value="false"> No</td>
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