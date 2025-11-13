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
    <link href="src\main\webapp\WEB-INF\views\getAllElezioni.jsp"  rel="stylesheet" type="text/css">
</head>

<body>
    
    <div>Tutte le elezioni</div>

    <table class="prova">
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
                
            </tr>
        </thead>

        <tbody>

            

                    <%-- Per iterare lista in jsp
                    1) SE DIRETTAMENTE IN html -> BASTA &{VARIABILE}
                    2) Se Dentro codice JAVA <% %> serve usare il request-getAttribute + Casting Esplicito --%>

                    <%
                        ArrayList<Elezione> allElezioni = (ArrayList<Elezione>) request.getAttribute("allElezioni");

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



                    </tr>

                    <%
                    } 
                    %> 

            
        </tbody>

        
    
    </table>


</body>

</html>