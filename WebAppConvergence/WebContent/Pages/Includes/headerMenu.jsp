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
			<div class="header_item"> 
				<a id="servidor" href="#">
					Servidor
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="servidor_opcoes" href="ServidorOpcoesServlet"> Opções </a>
						</li>
					</ul>
				</div>
			</div>
			<div class="header_item"> 
				<a id="ramais" href="#">
					Ramais SIP
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="adicionarRamais" href="CrudRamalSipServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarRamais" href="#"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			<div class="header_item"> 
				<a id="ramais" href="#">
					Ramais IAX
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="adicionarRamais" href="CrudRamalIAXServlet"> Adicionar </a>
						</li>
						<li>
							<a id="listarRamais" href="#"> Listar </a>
						</li>
					</ul>
				</div>
			</div>
			<div class="header_item"> 
				<a id="Dolor" href="#">
					Dolor
				</a>
				<div class="header_menu">
					<ul>
						<li>
							<a id="Lorem" href="#"> Link </a>
						</li>
						<li>
							Consectetur
						</li>
						<li>
							Adipiscing
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