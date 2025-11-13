<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.ministero.ministero.model.Elezione"%>

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

	<%
        String messaggio = (String) request.getAttribute("messaggio");

        if(messaggio == "Elezione Eliminata Correttamente"){                    
    %>
		<div class="my-2 alert alert-success" role="alert">
  			<b><%=messaggio%></b>.
		</div>
	<% } else if(messaggio == "Errore: Nessuna Elezione eliminata"){

	%>	
		<div class=" my-2 alert alert-danger" role="alert">
  			<b><%=messaggio%></b>.
		</div>	

	<%	} %>
	
	
	
	
	<div class="container-body">
	                  
        <h2 class="text-black">Tutte le elezioni</h2>
	    	 
		<%
		ArrayList<Elezione> allElezioni = (ArrayList<Elezione>) request.getAttribute("allElezioni");
		if(allElezioni != null) {
		%>   
	    <table class="table table-striped table-hover">
	        <thead >
	            <tr>
	                <th>Anno</th>
	                <th>Tipo</th>
	                <th>Descrizione Elezione</th>
	                <th>Data Elezione</th>
	                <th>Numero Schede</th>
	                <th>Schede Stato</th>
	                <th>Rimborso Spese Presidente</th>
	                <th>Percentuale Anticipo</th>
	                <th>Rendiconto</th>
	                <th>Covid</th>
	                <th>Elimina</th>
	            </tr>
	        </thead>
	
	        <tbody>
	
	                    <% 
	                          for( Elezione elezione : allElezioni ) {
	                    %> 
	
	                    <%-- QUESTO è TUTTO CICLO FOREACH --%>
	
	                    <tr >
	                    
	                        <td>
	                            <%= elezione.getAnno() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getTipo() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getDescrElezione() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getDataElezione() %>
	                        </td>
	                        
	                        <td>
	                            <%= elezione.getNumSchede() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getSchedeStato() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getRimborsoSpesePres() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getPercAnticipo() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getRendiconto() %>
	                        </td>
	
	                        <td>
	                            <%= elezione.getCovid() %>
	                        </td>
	
	                        <td>
	                           <a href="?idCancellazione=<%=elezione.getId()%>"> ELIMINA <a>
	                        </td>
	
	                        <%-- <td>
	                            <button value= "<%= elezione.getId() %>" onclick=deleteElezioneById(parseInt(this.value)) >
	                                Elimina
	                            </button>
	                        </td>
	
	                        <td>
	                            <button>
	                                Modifica
	                            </button>
	                        </td> --%>
	
	
	
	                    </tr>
	
	                    <%
	                      } 
             			%>
						            
	        </tbody>
	
	    </table>
	
		<nav class="pagination-wrapper justify-content-end" aria-label="Navigazione allineata a destra">
		  <ul class="pagination">
		    <li class="page-item disabled">
		      <a class="page-link" href="#" tabindex="-1" aria-hidden="true">
				<svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" class="bi bi-arrow-left-short" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5z"/>
				  <use href="/bootstrap-italia/dist/svg/sprites.svg#it-chevron-left"></use>
				</svg>
		        <span class="visually-hidden">Pagina precedente</span>
		      </a>
		    </li>
		    <li class="page-item">
		      <a class="page-link" href="#" aria-current="page">
		        <span class="d-inline-block d-sm-none">Pagina </span>1
		      </a>
		    </li>
		    <li class="page-item"><a class="page-link" href="#">2</a></li>
		    <li class="page-item"><a class="page-link" href="#">3</a></li>
		    <li class="page-item">
		      <a class="page-link" href="#">
		        <span class="visually-hidden">Pagina successiva</span>
				<svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" class="bi bi-arrow-right-short" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8z"/>
				  <use href="/bootstrap-italia/dist/svg/sprites.svg#it-chevron-right"></use>
				</svg>
		      </a>
		    </li>
		  </ul>
		</nav>
	
	    <%
	
	        String idCancellazione = request.getParameter("idCancellazione");
	    
	        if(idCancellazione!=null){ 
			
			String queryCancellazione = "http://localhost:8080/jsp/delete?idCancellazione="+idCancellazione;
		%>

	
	         <form method="GET"> 
	         <br>
	            <div > 
					<h3 class="text-black">
	                Sei sicuro di voler eliminare L'elezione con id: 
	                <%=idCancellazione%> e le relative Schede???
					<h3>
	            </div>
	
	            <a href= <%= queryCancellazione %> > Sì <a>
				
				<br>

	            <a href="http://localhost:8080/jsp/getAllElezioni"> NO <a>
	                
	            
	
	         </form>
		     <% } %> 
		     
           <% 
           }
   		   else { %> 
   		   <p class="fw-normal text-center table-result-null-space">Non è stato trovato nessun risultato</p>
   		   
   		   <% } %>
		    
		<jsp:include page="component/buttonBackBP.jsp" />
   
        </div>
		</div>
	</section>
	

    <jsp:include page="component/footerBP.jsp" />

	<script src="../static/bootstrap-italia/js/bootstrap-italia.bundle.min.js"></script>
</body>

</html>