<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="../Includes/headerCodigo.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		if ($("#columnTwo").height() > $("#columnOne").height()) {
			$("#columnOne").height($("#columnTwo").height());
		} else {
			$("#columnTwo").height($("#columnOne").height());
		}
	});
</script>

</head>

<jsp:include page="../Includes/headerMenu.jsp" />

<div class="pageBody">
	<div id="columnOne" class="pageBodyLeft"></div>
	<div id="columnTwo" class="pageBodyRight">
		<h3>Informações do Servidor</h3>
		<table cellspacing="0">
			<tr>
				<td class="left">Ip do Servidor</td>
				<td class="right"><input id="ipServidor" name="ipServidor"
					type="text" value="${servidor}" readonly="readonly"></td>
			</tr>
		</table>
	</div>
</div>

<jsp:include page="../Includes/footer.html" />
