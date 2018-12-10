<%--
  Created by IntelliJ IDEA.
  User: joaom
  Date: 10/12/2018
  Time: 01:05
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Estou aqui para mostrar info sobre musicas, albuns e artistas</title>
</head>
<body>
<%--c:forEach items="${primesBean.primes}" var="value"
/c:forEach
<h2><s:property value="registerBean.texto" /></h2>--%>
Procure o que quiser, seja o que procura, entre nesta aventura

<body>
<s:form action="searchmusic" method="post">
    <s:text name="Tipo de pesquisa(Artista/Album/Musica):" />
    <s:textfield name="search_type" /><br>
    <s:text name="Nome da pesquisa:"/>
    <s:password name="word" /><br>
    <s:submit value="Pesquisar" />
</s:form>


</body>
</html>
