<%@ page import="com.ministero.ministero.util.Messaggio"%>

<%

Messaggio messaggio = new Messaggio(); 
messaggio = (Messaggio) request.getAttribute("messaggio");


if (messaggio.getEsito().equals("success")) {

%>

  <div class="alert alert-success" role="alert">
    <%= messaggio.getDescrizione() %>
  </div>

<% 

} else if (messaggio.getEsito().equals("danger")) {

%>

  <div class="alert alert-danger" role="alert">
    <%= messaggio.getDescrizione() %>
  </div>

<%

} else if (messaggio.getEsito().equals("primary")) {

%>

  <div class="alert-space-top"></div>
<div class="alert alert-primary" role="alert">
    <%= messaggio.getDescrizione() %>
</div>

<%

} else if (messaggio.getEsito().equals("info")) {

%>

<div class="alert alert-info" role="alert">
    <%= messaggio.getDescrizione() %>
</div>

<%
} else if (messaggio.getEsito().equals("warning")) {
%>

<div class="alert alert-warning" role="alert">
    <%= messaggio.getDescrizione() %>
</div>

<%
} else {
}
%>






