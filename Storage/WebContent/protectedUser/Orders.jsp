<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>

<%
	Collection<?> orders = (Collection<?>) request.getAttribute("orders");
	if(orders == null){
		response.sendRedirect("./ordiniUtente");
		return;
	}
	ProductBean product = (ProductBean) request.getAttribute("product");
%>


<!DOCTYPE html>
<html>
<%@ page import= "java.util.*, it.unisa.product.ProductBean" %>
<head>
<meta charset="UTF-8">
<link href="ProductStyle.css" rel ="stylesheet" type="text/css">
<title>Ordini</title>
</head>
<body>
<h1>Ordini di <%= session.getAttribute("nome") %></h1>

<table border="1">
		<tr>
			<th>Numero ordine <a href="product?sort=idProdotto">Sort</a></th>
			<th>Data Ordine <a href="product?sort=nome">Sort</a></th>
            <th>Prezzo totale <a href="product?sort=prezzo">Sort</a></th>
			<th><a href="product?sort=prezzo">Stampa Fattura</a></th>
</tr>
</table>

</body>
</html>