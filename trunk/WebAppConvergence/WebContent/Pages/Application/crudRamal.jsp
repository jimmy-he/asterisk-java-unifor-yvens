<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="../Includes/header.jsp" />

<script type="text/javascript">
	$(document).ready(function () {
		boolAvancado = false;
		
		$("#hiddenAttr").hide();
		
		igualarDiv();
	});
	
	var boolAvancado;
	
	function igualarDiv(){
		if($("#columnTwo").height() > $("#columnOne").height())
		{
			$("#columnOne").height($("#columnTwo").height());
		}
		else
		{
			$("#columnTwo").height($("#columnOne").height());
		}
	}
	
	function avancado(){
		if(boolAvancado){
			$("#hiddenAttr").slideUp("slow", function(){igualarDiv();});
		}else{
			$("#hiddenAttr").slideDown("slow", function(){igualarDiv();});
		}
		boolAvancado = !boolAvancado;
	}
</script>

<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft">
	
	</div>
	
	<div id="columnTwo" class="pageBodyRight">
		<h3>${tarefa}</h3>
		
		<!-- Atributos que normalmente são modificados quando criamos um ramal -->
		<!-- String tag, String callerId, String username, String secret -->
		
		<table id="main_table" cellspacing="0">
			<tr> 
				<td class="left">
					Tag 
				</td>
				<td class="right">
					<input name="tag" type="text" value=""> 
				</td>
			</tr>
			<tr> 
				<td class="left">
					Caller Id 
				</td>
				<td class="right">
					<input name="callerId" type="text" value=""> 
				</td>
			</tr>
			<tr> 
				<td class="left">
					Username 
				</td>
				<td class="right">
					<input name="username" type="text" value=""> 
				</td>
			</tr>
			<tr> 
				<td class="left">
					Secret 
				</td>
				<td class="right">
					<input name="secret" type="password" value=""> 
				</td>
			</tr>
		</table>
		
		<!-- Atributos avançados da criação de um ramal -->
		<!-- RamalType type, boolean canReinvite, String context, String dtmfMode, int callLimit, boolean nat -->
		<!-- Valores default para os atributos do ramal -->
		<!-- RamalType.FRIEND, false, "LOCAL", "rfc2833", 2, false); -->
		<div id="hiddenAttr">
			<table cellspacing="0">
				<tr class="hidden_tr"> 
					<td class="left">
						Ramal Type
					</td>
					<td class="right">
						<select size="1" name="D1">
							<option selected value="Selecione">Selecione!</option>
							<option value="2000">2000</option>
							<option value="2001">2001</option>
						</select>
					</td>
				</tr>
				<tr class="hidden_tr"> 
					<td class="left">
						Can Reinvite
					</td>
					<td class="right">
						<input name="canReinvite" type="radio" value="true"> Yes <input name="canReinvite" checked="checked" type="radio" value="false"> No
					</td>
				</tr>
				<tr class="hidden_tr"> 
					<td class="left">
						Context 
					</td>
					<td class="right">
						<input name="context" type="text" value="LOCAL"> 
					</td>
				</tr>
				<tr class="hidden_tr"> 
					<td class="left">
						DtmfMode 
					</td>
					<td class="right">
						<input name="dtmfMode" type="text" value="rfc2833"> 
					</td>
				</tr>
				<tr class="hidden_tr"> 
					<td class="left">
						CallLimit 
					</td>
					<td class="right">
						<input name="callLimit" type="text" value="2"> 
					</td>
				</tr>
				<tr class="hidden_tr"> 
					<td class="left">
						Nat
					</td>
					<td class="right">
						<input name="nat" type="radio" value="true"> Yes <input name="nat" checked="checked" type="radio" value="false"> No
					</td>
				</tr>
			</table>	
		</div>
		
		<table cellspacing="0">
			<tr>
				<td class="buttons" colspan="2">
					<input type="submit" value="${btnSubmit}">
					<input type="reset" value="Limpar">
					<input type="button" value="Avançado" onclick="avancado();">
				</td>
			</tr>
		</table>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />  