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
    
    <link href="style.css" media="all" rel="stylesheet" type="text/css">

</head>

<body>


        <% if(parametri.hasMoreElements()){ 

            %>

            <form action="/jsp/conferma" method="POST">

            <label for="nome">Anno:</label>
            <input style="color: green;" type="number" id="anno" name="anno" value= <%= request.getParameter("anno") %>>
        
            <br>
            
            <label for="codElezione">Codice Elezione:</label>
            <input style="color: green;" type="number" id="codElezione" name="codElezione" value= <%= request.getParameter("codElezione") %>>

            <br>
            <br>

            <label for="formConferma">Sei Sicuro di voler Creare questa Elezione?</label>

             <br>
             <br>

            <input type="submit" name="formConferma" value="Si">

            </form>

            <a href="http://localhost:8080/jsp/createElezione">No</a>


        <%} else {%> 

            <form>

            <label for="anno">Anno:</label>
            <input type="number" id="anno" name="anno" >
        
            <br>
            
            <label for="codElezione">Codice Elezione:</label>
            <input type="number" id="codElezione" name="codElezione">
            
            <br>
            <br>

            <input type="submit" value="Invia Dati Per Conferma">

            </form>


        <% } %>

        


    </form>

    
    <%
        String messaggio = (String) request.getAttribute("messaggio");

        if(messaggio != null){ 
                    
    %>

       <div style="color: green;"> <%= messaggio %> </div>

    <% 
        }
    %>

    

    <jsp:include page="component/footer.jsp" />


</body>

</html>