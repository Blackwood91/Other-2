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

                    

                <%-- <button onclick=mostraAlert()>
                    Mostra Alert
                </button>     --%>
           

                    <%-- Per iterare lista in jsp
                    1) SE DIRETTAMENTE IN html -> BASTA &{VARIABILE}
                    2) Se Dentro codice JAVA <% %> serve usare il request-getAttribute + Casting Esplicito --%>
                    <%-- <%
                        if(request.getParameter("id").length()>0){
                            System.out.println("sig");
                        }
                    %> --%>


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

            <%-- <script>

                const dialog = document.getElementById("dialog");

                function mostraAlert(){
                    dialog.showModal()
                    console.log("ciao");
                }

                async function deleteElezioneById(id){

                    console.log(id);

                    const response = await fetch("http://localhost:8080/api/elezione/delete?id="+id, {
                        method: "DELETE"
                    });

                    if (response.ok){

                    console.log("Elezione eliminata")
                    location.reload()
                
                    } else console.log("Errore connessions")

                }


                    
            </script> --%>

            
        </tbody>

        
    
    </table>

    <%

        String idCancellazione = request.getParameter("idCancellazione");
    
        if(idCancellazione!=null){ %>


         <form method="GET"> 
         <br>
            <div> 
                Sei sicuro di voler eliminare L'elezione con id: 
                <%=idCancellazione%> e le relative Schede???
            </div>

            <a href="http://localhost:8080/api/elezione/delete?id="+idCancellazione > Sì <a>

            <a href="http://localhost:8080/jsp/getAllElezioni"> NO <a>
                
            

         </form>
        <%
        }
    %>

    <jsp:include page="component/footer.jsp" />


</body>

</html>