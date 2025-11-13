<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.ministero.ministero.model.Elezione"%>
<%@ page import="com.ministero.ministero.model.SchedeElez"%>
<%@ page import="com.ministero.ministero.util.Messaggio"%>
<%@ page import="java.util.Optional"%>




<%
    // Ottieni un'enumerazione dei nomi dei parametri
    java.util.Enumeration<String> parametri = request.getParameterNames();

	
%>


<!DOCTYPE html>

<html lang="it">

<head>
    <meta charset="UTF-8">
    <link href="../static/bootstrap-italia/css/bootstrap-italia.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="../static/bootstrap-italia/css/spese-elettorali.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>

    <jsp:include page="component/headerBP.jsp" />

    <section id="head-section">
	<div class="container-fluid">
	
	<%-- messaggi alert --%>
	<%-- <jsp:include page="component/messageBP.jsp" /> --%>
	
		<div class="container-body">
	                  
            <h2 class="text-black">Inserimento elezione</h2>
			<% 

			

			//getParameter ZONE --> grabbo i parametri
			String conferma = request.getParameter("conferma");
			boolean confermaOn = (conferma != null) ? true : false;

			String creaDaSecondaSchedeElezInPoi = request.getParameter("creaDaSecondaSchedeElezInPoi");
			boolean creaDaSecondaSchedeElezInPoiOn = (creaDaSecondaSchedeElezInPoi != null) ? true : false;


			//getObject ZONE --> grabbo gli object
			Messaggio messaggio = new Messaggio(); 
			messaggio = (Messaggio) request.getAttribute("messaggio");

			String elezioneEprimaSchedeElezCreate = (String) request.getAttribute("elezioneEprimaSchedeElezCreate");
			boolean elezioneEprimaSchedeElezCreateOn = (elezioneEprimaSchedeElezCreate != null) ? true : false;

			//Qui salvo l'Elezione che ho creato nel database!
			Elezione elezioneDaRitornare = new Elezione(); 
			elezioneDaRitornare = (Elezione) request.getAttribute("elezioneDaRitornare");

			

			System.out.println("!!!!!!!!!!! "+elezioneEprimaSchedeElezCreateOn);
			

			// DA QUI FINO ALL'ELSE VISUALIZZO SOLO SE HO PARAMETRI
			//  FASE 4 --> SE SCELGO I AGGIUNGERE PIù DI UNA SchedeElez
			if(parametri.hasMoreElements() && creaDaSecondaSchedeElezInPoiOn ){ %>
				
				<jsp:include page="component/messageBP.jsp" />

				<div>
				Sono qui
				</div>

			<%

			  //+++ FASE 3: PRIMA Elezione e prima SchedeElez Create +++   
			} else if (parametri.hasMoreElements() && elezioneEprimaSchedeElezCreateOn) { 
				
				//L'elezione la Grabbo SOPRA

				//Elezione elezioneDaRitornare = new Elezione(); 
				//elezioneDaRitornare = (Elezione) request.getAttribute("elezioneDaRitornare");
		
			%>
					<br>

					<jsp:include page="component/messageBP.jsp" />


					<div class="form-space-top border border-3 border-dark p-2">

							<div class="">
			    				<span >Descrizione Elezione: </span>
								<span style="color: green" ><%=elezioneDaRitornare.getDescrElezione()%></span>
			    				<%-- <input hidden type="text" name="descrElezione" style="color: green;" value="<%=request.getParameter("descrElezione")%>" >  --%>
			  				</div>

							<div class="">
			    				<span >Anno Elezione: </span>
								<span style="color: green" ><%=elezioneDaRitornare.getAnno()%></span>
			    				<%-- <input hidden type="number" name="anno" style="color: green;" value="<%=request.getParameter("anno")%>" >  --%>
			  				</div>

							<div class="">
			    				<span >Tipo Elezione: </span>

								<%
									String tipoFullName = (elezioneDaRitornare.getTipo().equals("A")) ? "Accorpata" : "Semplice";
								%>

								<span style="color: green" ><%=tipoFullName%></span>
			    				<%-- <input hidden type="text" name="tipo" style="color: green;" value="<%=request.getParameter("tipo")%>" >  --%>
			  				</div>

							<div class="">
			    				<span >Data Elezione: </span>
								<span style="color: green" ><%=elezioneDaRitornare.getDataElezione()%></span>
			    				<%-- <input hidden type="date" name="dataElezione" style="color: green;" value="<%=request.getParameter("dataElezione")%>" >  --%>
			  				</div>


							<div class="">
			    				<span >Avrà Rendiconto: </span>
								<% if(elezioneDaRitornare.getRendiconto().equals("S")) { 
								%>
									<span style="color: green" > Sì </span>
									<%-- <input hidden type="text" name="rendiconto" value="N" style="color: green;" >  --%>
								<%		
								} else {
								%>
									<span style="color: green" > No </span>
			    					<%-- <input hidden type="text" name="rendiconto" value="S" style="color: green;">  --%>
								<%
								}
								%>
			  				</div>

							<div class="">
			    				<span >Sarà sotto COVID: </span>
			    				<% if(elezioneDaRitornare.getCovid().equals("S")) { 
								%>
									<span style="color: green" > Sì </span>
									<%-- <input hidden type="text" name="covid" value="N" style="color: green;" >  --%>
								<%		
								} else {
								%>
									<span style="color: green" > No </span>
			    					<%-- <input hidden type="text" name="covid" value="S" style="color: green;">  --%>
								<%
								}
								%>
			  				</div>

							<!-- <Numero Schede e Schede Stato  -->
							<div class="">
			    				<span >Numero Schede Totali: </span>
								<span style="color: green" ><%=elezioneDaRitornare.getNumSchede()%></span>
			    				<%-- <input hidden type="date" name="dataElezione" style="color: green;" value="<%=request.getParameter("dataElezione")%>" >  --%>
			  				</div>

							<div class="">
			    				<span >Numero Schede Stato: </span>
								<span style="color: green" ><%=elezioneDaRitornare.getSchedeStato()%></span>
			    				<%-- <input hidden type="date" name="dataElezione" style="color: green;" value="<%=request.getParameter("dataElezione")%>" >  --%>
			  				</div>

					</div>

					<br>

					 <!-- REINDERIZZO SCHEDE ELEZ IN UNA TABLE  -->

					<%
						List<SchedeElez> listaSchedeElezDaRitornare = new ArrayList<>();
						listaSchedeElezDaRitornare = (List<SchedeElez>) request.getAttribute("listaSchedeElezDaRitornare");
					
					%>

							
					
							<table class="table">
								<thead>
									<tr>
										<th scope="col"></th>
										<th scope="col">Tipo Schede Elezione</th>
										<th scope="col">Numero Schede Elezione</th>
									</tr>
								</thead>
								<tbody>

									<% 
										for(SchedeElez schedeElez : listaSchedeElezDaRitornare){ 
									%>

									<tr>
										<th scope="row">1</th>
										<td><%=schedeElez.getTipoSchedeNomeCompleto()%></td>
										<td><%=schedeElez.getNumSchede()%></td>
									</tr>			

									<%
									}
									%>

								</tbody>
							</table>


							 <!-- qui Scelgo di aggiungere nuova Schede Elezionea -> l'action ricarica la pagina
							 MA CON un parametro hidden creaDaSecondaSchedeElezInPoi  E GLI RIDò elezioneEprimaSchedeElezCreateOn per rientrare qui-->
							<%--+++ RICOMINCIA DA QUI voglio --> ripassare per il controller --%>
							<form action="createElezione">
								<input hidden id="id" name="id" value="<%=elezioneDaRitornare.getId()%>">
								<input hidden id="elezioneEprimaSchedeElezCreateOn" name="elezioneEprimaSchedeElezCreateOn" value="elezioneEprimaSchedeElezCreateOn">
								<input hidden id="creaDaSecondaSchedeElezInPoi" name="creaDaSecondaSchedeElezInPoi" value="creaDaSecondaSchedeElezInPoi">
								<button type="submit" class="btn btn-outline-primary btn-sm btn-me">Aggiungi Altre Schede Elezione +</button>
							</form>

							<br>
							<br>

			<%
			//<!-- +++FASE 2: CONFERMA +++  -->
			} else if (parametri.hasMoreElements() && confermaOn){ %>
				

				 <!-- String conferma = (String)request.getParameter("conferma"); 

				 +++ HO COMMENTATO QUI MODIFICA QUI+++  -->


				<form type="submit" action="/jsp/creaPrimaSchedeElez" method="POST" > 
					<div class="form-space-top border border-3 border-dark p-2">
						
							<div class="">
			    				<span >Descrizione Elezione: </span>
								<span style="color: green" ><%=request.getParameter("descrElezione")%></span>
			    				<input hidden type="text" name="descrElezione" style="color: green;" value="<%=request.getParameter("descrElezione")%>" > 
			  				</div>

							<div class="">
			    				<span >Anno Elezione: </span>
								<span style="color: green" ><%=request.getParameter("anno")%></span>
			    				<input hidden type="number" name="anno" style="color: green;" value="<%=request.getParameter("anno")%>" > 
			  				</div>

							<div class="">
			    				<span >Tipo Elezione: </span>
								<span style="color: green" ><%=request.getParameter("tipo")%></span>
			    				<input hidden type="text" name="tipo" style="color: green;" value="<%=request.getParameter("tipo")%>" > 
			  				</div>

							<div class="">
			    				<span >Data Elezione: </span>
								<span style="color: green" ><%=request.getParameter("dataElezione")%></span>
			    				<input hidden type="date" name="dataElezione" style="color: green;" value="<%=request.getParameter("dataElezione")%>" > 
			  				</div>


							<div class="">
			    				<span >Avrà Rendiconto: </span>
								<% if(request.getParameter("rendiconto") == null) { 
								%>
									<span style="color: green" > Sì </span>
									<input hidden type="text" name="rendiconto" value="N" style="color: green;" > 
								<%		
								} else {
								%>
									<span style="color: green" > No </span>
			    					<input hidden type="text" name="rendiconto" value="S" style="color: green;"> 
								<%
								}
								%>
			  				</div>

							<div class="">
			    				<span >Sarà sotto COVID: </span>
			    				<% if(request.getParameter("covid") == null) { 
								%>
									<span style="color: green" > Sì </span>
									<input hidden type="text" name="covid" value="N" style="color: green;" > 
								<%		
								} else {
								%>
									<span style="color: green" > No </span>
			    					<input hidden type="text" name="covid" value="S" style="color: green;"> 
								<%
								}
								%>
			  				</div>

					</div>

						
						 <!-- +++VEDI+++ 
						 <input type="submit" name="formConferma" value="Si">  -->


					 <!-- <div class="button-bottom-space">

						<span class="text-warning">
							Vuoi Creare questa Elezione con questi Parametri?
						</span>

						<input type="submit" class="btn btn-primary btn-lg btn-me" value="Invia">
						<a href="http://localhost:8080/jsp/createElezione">No</a>
						</div>
		    		
					</div>  -->

					<br>
					
					<h4>Inserire le Relative Schede Elezione:</h4>

					 <!-- FORM  senza tag <form> PER INSERIRE SCHEDE ELEZ -->
					 <!-- <form type="submit" action="/jsp/createSchedeElez" method="POST">  -->
  						<div class="row border border-dark py-4 m-2">
						
    					 <!-- <div class="form-group col-md-6 mt-3">
      						<label for="tipoScheda">Tipo Scheda Elezione</label>
      						<input type="text" class="form-control" id="tipoScheda" name="tipoScheda">
						</div>  -->

						<br>
						<br>

						<div class="form-group col-md-6 mt-3">
							<div class="select-wrapper">
							<%-- <label for="tipoScheda">Tipo Schede Elezione</label> --%>
								<select id="tipoScheda" name="tipoScheda">
									<option value="" disabled selected>Tipo Scheda Elezione</option>
									<option value="PC">POLITICHE CAMERA</option>
									<option value="PS">POLITICHE SENATO</option>
									<option value="EU">EUROPEE</option>
									<option value="AR">REGIONALI</option>
									<option value="AC">COMUNALI</option>
                                    <option value="RS">REFERENDUM STATALE</option>
									<option value="RL">REFERENDUM LOCALE</option>
								</select>
							</div>
						</div>

						<div class="form-group col-md-6 mt-3">
      						<label for="numSchede">Numero Schede</label>
      						<input type="number" class="form-control" id="numSchede" name="numSchede">
    					</div>

						<div class="">
							 <!-- Inserisco INPUT HIDDEN per determinare che ho aggiunto Scheda  -->
							<input hidden id="addScheda" name="addScheda" value="addScheda" >
							<button type="submit" class="btn btn-outline-primary btn-lg btn-me">Aggiungi altre Schede Elezione +</button>
		    			</div> 
					
    					</div>
					 <!-- </form>  -->



            		<!-- <a href="http://localhost:8080/jsp/createElezione">No</a>  -->


				</form>

			<%
			
			} else {

			%>
		
		<form action="" method="GET">
			
			<h4>FORM INIZIALE</h4>

            
            <div class="form-space-top">
			  <div class="form-group">
			    <label for="descrElezione">Descrizione Elezione</label>
			    <input  type="text" class="form-control" id="descrElezione" name="descrElezione">
			  </div>

			  <div class="form-group">
			    <label for="anno">Anno</label>
			    <input  type="number" data-bs-input class="form-control" id="anno" name="anno">
			  </div>

				<div class="select-wrapper">
			  	  <!-- mettere dentro label for e fuori defaultSelectDisabled, fare la stessa cosa anche in select id per disabilitare -->
				  <label for="tipo">Tipo Elezione</label>
				  <select   id="tipo" name="tipo">
				    <option value="" disabled selected>--</option>
				    <option value="A">Accorpata</option>
				    <option value="S">Semplice</option>
				    
				  </select>
				</div>

				<br>
				<br>

			  
			  <div class="form-group">
				    <label class="active" for="dataElezione">Data Elezione</label>
				    <input type="date" id="dataElezione" name="dataElezione">
			  </div>
		
			  <div class="form-check">
			    <input type="checkbox" value="S" name="rendiconto" id="rendiconto" >
			    <label for="rendiconto">Avrà Rendiconto?</label>
			  </div>

			   <div class="form-check">
			    <input type="checkbox" value="S" id="covid" name="covid" >
			    <label for="covid">Elezioni con COVID?</label>
			  </div>



					
		
		  
		
			</div>


				<!-- AL PRIMO CARICAMENTO DELLA PAGINA NON C'è 
				 ++++BUTTONE Invia TOTALE FINALE+++  -->

				<div class="button-bottom-space">
					<input type="hidden" id="conferma" name="conferma" value="conferma" >
					<button type="submit" class="btn btn-primary btn-lg btn-me">Aggiungi Schede Elezione</button>
				</div> 

		    </div>

		</form>

		<br>
		
		<%-- ++++++ CHIUDO l' else' +++++++ --%>
		<%
		}
		%>

			

		    
		    <jsp:include page="component/buttonBackBP.jsp" />
		    
        </div>
        
		</div>
	</section>
	
    <jsp:include page="component/footerBP.jsp" />

	<script src="../static/bootstrap-italia/js/bootstrap-italia.bundle.min.js"></script>
</body>

</html>