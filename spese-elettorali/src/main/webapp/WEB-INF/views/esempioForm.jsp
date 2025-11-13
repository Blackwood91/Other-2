<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>

<%
    // Ottieni un'enumerazione dei nomi dei parametri
    java.util.Enumeration<String> parametri = request.getParameterNames();
%>

<html lang="it">
    <head>
       
    </head>
    <body>

            
    <section class="mt-3 ml-2 mr-2 mb-3">


    <h1>Tabella Prodotti</h1>
    
    <table border="1">
        <thead>
            <tr>
                <th>ID Prodotto</th>
                <th>Nome Prodotto</th>
                <th>Prezzo</th>
            </tr>
        </thead>
        <tbody>
            <%-- Esempi di dati del prodotto (puoi ottenere questi dati da un database o da altre fonti)
            
                 Per importare un'altra jsp nella stessa jsp
                 <%@ include file="../common/nav.jsp" %>
            --%>
            <%
                List<String[]> products = new ArrayList<>();
        
                // Creazione di alcuni array di stringhe e aggiunta all'ArrayList
                String[] array1 = {"Inizio", "Elemento 2", "Elemento 3"};
                String[] array2 = {"Apple", "Banana", "Orange"};
                String[] array3 = {"Casa", "Appartamento", "Villa"};
                String[] array4 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array5 = {"Apple", "Banana", "Orange"};
                String[] array6 = {"Casa", "Appartamento", "Villa"};
                String[] array7 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array8 = {"Apple", "Banana", "Orange"};
                String[] array9 = {"Casa", "Appartamento", "Villa"};
                String[] array10 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array11 = {"Medio", "Banana", "Orange"};
                String[] array12 = {"Casa", "Appartamento", "Villa"};
                String[] array13 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array14 = {"Apple", "Banana", "Orange"};
                String[] array15 = {"Casa", "Appartamento", "Villa"};
                String[] array16 = {"Casa", "Appartamento", "Villa"};
                String[] array17 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array18 = {"Apple", "Banana", "Orange"};
                String[] array19 = {"Casa", "Appartamento", "Villa"};
                String[] array20 = {"Casa", "Appartamento", "Villa"};
                String[] array21 = {"Elemento 1", "Elemento 2", "Elemento 3"};
                String[] array22 = {"Apple", "Banana", "Orange"};
                String[] array23 = {"Casa", "Appartamento", "Villa"};
                String[] array24 = {"Fine", "Appartamento", "Villa"};
  

                products.add(array1);
                products.add(array2);
                products.add(array3);
                products.add(array4);
                products.add(array5);
                products.add(array6);
                products.add(array7);
                products.add(array8);
                products.add(array9);
                products.add(array10);
                products.add(array11);
                products.add(array12);
                products.add(array13);
                products.add(array14);
                products.add(array15);
                products.add(array16);
                products.add(array17);
                products.add(array18);
                products.add(array19);
                products.add(array20);
                products.add(array21);
                products.add(array22);
                products.add(array23);
                products.add(array24);
     
                
                

                int recordsPerPage = 10; // Numero di righe per pagina
                //Per prendere il numero della pagina corrente 
                String paginaCorrente = request.getParameter("page");
                int currentPage = (paginaCorrente != null) ? Integer.parseInt(paginaCorrente) : 1;
                int startRecord = (currentPage - 1) * recordsPerPage;
                int endRecord = Math.min(startRecord + recordsPerPage, products.size());
                //List<String> currentPageData = products.subList(startRecord, endRecord);
                
                // Creazione di una nuova lista contenente la sotto-lista
                List<String[]> sottoLista = new ArrayList<>();
        
                for (int i = startRecord; i < endRecord; i++) {
                    sottoLista.add(products.get(i));
                }
                
                int totalPages = (int) Math.ceil((double) products.size() / recordsPerPage);
            
            %>
                        
            
    <% 

        //Per ciclare le righe della lista in tabella
        for (String[] row : sottoLista) { %>
            <tr>
                <td><%= row[0] %></td>
                <td><%= row[1] %></td>
                <td><%= row[2] %></td>
            </tr>
        <% } 

            // Paginazione %>
            
        </tbody>
        </table>

    <div>

        <%
        int contatore = 0;
        String queryPath = "?";
        while (parametri.hasMoreElements()) {
            String nomeParametro = parametri.nextElement();
            String valoreParametro = request.getParameter(nomeParametro);
            if(nomeParametro.equalsIgnoreCase("page") == false) {
                if(contatore == 0) {
                    queryPath = queryPath + nomeParametro +  "=" + valoreParametro;
                }
                else { 
                    //Dai succesivi parametri dopo il primo
                    queryPath = queryPath + "&" + nomeParametro +  "=" + valoreParametro;
                }
            }
        }
       
        //Per ogni volta che viene passato più di un parametro verra riportaa anche la & per fare in modo di aggiungere il parametro della pagina
        queryPath = queryPath.length() > 1 ? queryPath + "&" : queryPath;
        if (currentPage > 1) { 
            String pathPage = queryPath + "page=" + (currentPage - 1);
        %>
            <a href="<%= pathPage %>">Pagina precedente</a>
        <% } %>

        <% if (currentPage < totalPages) { 
               String pathPage = queryPath + "page=" + (currentPage + 1);

        %>
            <a href="<%= pathPage %>">Pagina successiva</a>
        <% } %>
            
            
            
        <%//-------------- PER MOSTRARE CAMPI VISIBILE O INVISIBILI  
        //IL CAMPO SARA' VISIBILE IN CHIARO 
        String nomeCampo = request.getParameter("selezione");
        //SE SARA' NULL IL CAMPO INVIATO NON E' STATO SELEZIONATO
        boolean campoVisible = (nomeCampo != null) ? true : false; 
        
        //CREARE UN FORM OGNI QUAL VOLTA SI VOGLIANO MOSTRARE O NASCONDERE I CAMPI
        %>
        <form action="" method="get">
            <!-- Checkbox con JSP -->
            <% 
            //Lo si richiama se no vengono persi i parametri dopo la prima volta che viene utilizzato
            parametri = request.getParameterNames();

            while (parametri.hasMoreElements()) {
                String nomeParametro = parametri.nextElement();
                String valoreParametro = request.getParameter(nomeParametro);
                if(nomeParametro.equalsIgnoreCase("selezione") == false) {
                %>
                     <input hidden type="text" name="<%= nomeParametro %>" value="<%= valoreParametro %>">
                <%
                }
            }
            %>
            <input type="checkbox" name="selezione" >
            <label for="mioCheckbox">Seleziona questa casella</label>
    
            <!-- Altri campi del form qui -->
    
            <input type="submit" value="Invia">
        </form>

  
        <% if (campoVisible) { %>
            <h1> Visibile </h1>
            <input type="text" name="provaInserimentoCampo" placeholder="Inserisci qualcosa...">
        <% } %>

    </div>
    
    <%
    String conferma = request.getParameter("messaggioConferma");
    String noConferma = request.getParameter("noConferma");
    if(conferma == null || conferma.equals("true") == false) {
    %>
        <form action="" method="get">
      <%       
            //Lo si richiama se no vengono persi i parametri dopo la prima volta che viene utilizzato
            parametri = request.getParameterNames();

            while (parametri.hasMoreElements()) {
                String nomeParametro = parametri.nextElement();
                String valoreParametro = request.getParameter(nomeParametro);
                if(nomeParametro.equalsIgnoreCase("selezione") == false) {
                %>
                     <input hidden type="text" name="<%= nomeParametro %>" value="<%= valoreParametro %>">
                <%
                }
            }
            %>


            <%--!!!!!!!!!!!!!!!!!!!!! FORM !!!!!!!!!!!!!!!!--%>

            <!-- Campi del modulo per l'invio dei dati -->
            
            <label for="nome">Nome:</label>
            <input type="text" id="nomeConferma" name="nomeConferma"><br>
            
            <label for="email">Email:</label>
            <input type="text" id="emailConferma" name="emailConferma"><br>
    
            <!-- Aggiungi un campo nascosto per indicare lo stato del messaggio di conferma -->
            <input type="hidden" id="messaggioConferma" name="messaggioConferma" value="true">
    
            <input type="submit" value="Invia Dati Per Conferma">
        </form>
    
        <%-- Verifica se il messaggio di conferma è stato impostato --%>
        <%  
        }
        //IN CASO DI PRIMO INVIO PER CONFERMA
        else if (noConferma == null || noConferma.equals("true")) {
            //CONTROLLO DEI CAMPI INSERITI SE SONO VALORIZZATI O NO IN CASO RICARICARE LA PAGINA SENZA IL PARAMETRO messaggioConferma E CON IL MESSAGE USER CHE SEGNALA IL PROBLEMA
           
            //In caso di no da parte dell'utente
            parametri = request.getParameterNames();

            contatore = 0;
            queryPath = "?";
            while (parametri.hasMoreElements()) {
                String nomeParametro = parametri.nextElement();
                String valoreParametro = request.getParameter(nomeParametro);
                if(contatore == 0) {
                    queryPath = queryPath + nomeParametro +  "=" + valoreParametro;
                }
                else { 
                    //Dai succesivi parametri dopo il primo
                    queryPath = queryPath + "&" + nomeParametro +  "=" + valoreParametro;
                }
                
            }
            String pathPage = queryPath + "noConferma=true";
            //Fine no -----------------------------------------------------
            
        %>
        <div style="color: green;">Sicuro di confermare l'invio dei dati?</div>

        <form action="metodoInserimento" method="get">
            <!-- Campi del modulo per l'invio dei dati -->
            <label style="color: green;" for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= request.getParameter("nomeConferma") %>"><br>
            
            <label style="color: green;" for="email">Email:</label>
            <input type="text" id="email" name="email" value="<%= request.getParameter("emailConferma") %>"><br>
    
            <input type="submit" value="Si">
            <% //IN QUESTO CASO BISOGNA AGGIUNGERE UN PARAMETRO CHE  %>
            <a href="<%= pathPage %>">No</a>

        </form>        
        
        <%
        }
        %>

 
        </section>
    </body>   
</html>