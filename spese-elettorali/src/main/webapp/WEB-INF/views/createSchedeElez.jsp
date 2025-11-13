<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.ministero.ministero.model.Elezione"%>
<%@ page import="com.ministero.ministero.model.SchedeElez"%>


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
	
                <%-- TUTTI I FORM VANNO DENTRO QUESTO DIV --%>
		        <div class="container-body">

                    <h2 class="text-black">Inserimento Schede Elezione</h2>

                        <br>

                        <form type="submit" action="/jsp/provaCavia" method="POST">

                            <%-- PRIMA SCHEDA ELEZ --%>
                        	<div class="container-body">
  				                <div class="row border border-dark py-4">

                                    <% int contatore = 1; %>

					                <span>Scheda Elezione 1:</span>

    				                <div class="form-group col-md-6 mt-3">
      					                <label for="tipoScheda1">Tipo Scheda Elezione</label>
      					                <input type="text" class="form-control" id="tipoScheda1" name="tipoScheda">
					                </div>

					                <div class="form-group col-md-6 mt-3">
      					                <label for="numSchede1">Numero Schede</label>
      					                <input type="number" class="form-control" id="numSchede1" name="numScheda">
    				                </div>
					
    			                </div>

				                <br>

                                <%-- BUTTON SECONDARIO AGGIUNGI ALTRA SCHEDE ELEZ --%>
				                <div class="">
						            <input type="hidden" id="addScheda" name="addScheda" value="addScheda" >
						            <button type="submit" class="btn btn-outline-primary btn-lg btn-me">Aggiungi altra Scheda Elezione +</button>
		    	                </div> 

				                <br>

			                </div>

                        
                            <%-- BOTTONE PRINCIPALE SUBMIT FORM --%>
                            <div class="button-bottom-space">
				                <input type="hidden" id="conferma" name="conferma" value="conferma" >
				                <button type="submit" class="btn btn-primary btn-lg btn-me">Invia</button>
		                    </div>

                        </form>
	                  
		                <jsp:include page="component/buttonBackBP.jsp" />
		    
                </div>
        
		    </div>

	    </section>
	
    <jsp:include page="component/footerBP.jsp" />

	<script src="../static/bootstrap-italia/js/bootstrap-italia.bundle.min.js"></script>

</body>

</html>
