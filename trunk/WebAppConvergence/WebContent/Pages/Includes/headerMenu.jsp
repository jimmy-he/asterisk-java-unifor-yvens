<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<!-- Header default da aplicação web -->

	<div id="pageHeader">
		<div class="logoHeader"> 
			<img alt="" src="Pages/Resources/logoAsteroid2.png" style="float: right;">
		</div>
	
		<div class="emptyWhiteSpace" style="padding-top: 40px;"> </div>
		
		<div class="greyLine"> </div>
		
		<div class="header">
			<div class="header_item"> 
				<a id="inicio" href="IndexServlet">
					Início
				</a>
			</div>
			<!--  Removido
			<div class="header_item"> 
				<a id="servidor" href="#">
					Servidor
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="servidor_opcoes" href="ServidorOpcoesServlet"> Informações </a>
						</li>
					</ul>
				</div>
			</div>
			-->
			<div class="header_item"> 
				<a id="Filas" href="#">
					Atendimento
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="criarFila" href="CrudWaitQueueServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarFila" href="ListWaitQueueServlet"> Listar </a>
						</li>
						<li class="header_menu_grouper">
							Agentes
						</li>
						<li>
							<a id="xxx" href="#"> Adicionar </a>
						</li>
						<li>
							<a id="xxx" href="#"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="header_item"> 
				<a id="Dolor" href="#">
					Discagem
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="planoDiscagem" href="CrudDialPlanServlet"> Adicionar </a>
						</li>
						<li>
							<a id="planoDiscagem" href="ListDialPlanServlet"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="header_item"> 
				<a id="Conferencia" href="#">
					Conferência
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="criarConferencias" href="CrudConferenceRoomServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarConferencias" href="ListConferenceRoomServlet"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="header_item"> 
				<a id="ramais" href="#">
					Ramais
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="monitorRamais" href="RamalMonitorServlet"> Monitoramento </a>
						</li>
						<li class="header_menu_grouper">
							SIP
						</li>
						<li>
							<a id="adicionarRamais" href="CrudRamalSipServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarRamais" href="TableRamalSipServlet"> Listar </a>
						</li>
						<li class="header_menu_grouper">
							IAX
						</li>
						<li>
							<a id="adicionarRamais" href="CrudRamalIAXServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarRamais" href="TableRamalIAXServlet"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			
		</div>
	</div>
	<div class="hardGreyLine">
	</div>

<c:if test="${!empty error}">
	<jsp:include page="error.jsp" />
</c:if>	
<c:if test="${!empty feedback}">
	<jsp:include page="feedback.jsp" />
</c:if>