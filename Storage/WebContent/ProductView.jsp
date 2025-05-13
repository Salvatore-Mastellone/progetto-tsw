<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	session ="true"%>
	

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) {
	response.sendRedirect("./product");	
		return;
	}
	ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.unisa.product.ProductBean,it.unisa.product.Carrello"%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="ProductStyle.css" rel="stylesheet" type="text/css">
	<title>Storage DS/BF</title>
</head>

<body>
	<h2>Products</h2>
	<a href="product">List</a>
	<table border="1">
		<tr>
			<th>Code <a href="product?sort=idProdotto">Sort</a></th>
			<th>Name <a href="product?sort=nome">Sort</a></th>
            <th>Price <a href="product?sort=prezzo">Sort</a></th>

			<th>Action</th>
		</tr>
		<%
			if (products != null && !products.isEmpty()) {
				Iterator<?> it = products.iterator();
				while (it.hasNext()) {
					ProductBean bean = (ProductBean) it.next();
		%>
		<tr>
			<td><%=bean.getIdProdotto()%></td>
			<td><%=bean.getNome()%></td>
			<td><%=bean.getDescrizione()%></td>
			<td><a href="product?action=delete&id=<%=bean.getIdProdotto()%>">Delete</a><br>
				<a href="product?action=read&id=<%=bean.getIdProdotto()%>">Details</a><br>
				<a href="product?action=add&id=<%=bean.getIdProdotto()%>">AddCarello</a></td>
		</tr>
		<%
				}
			} else {
		%>
		<tr>
			<td colspan="4">No products available</td>
		</tr>
		<%
			}
		%>
	</table>

    <h2>Details</h2>
	<%
		if (product != null) {
	%>
	<table border="1">
		<tr>
			<th>Code</th>
			<th>Nome</th>
			<th>Categoria</th>
			<th>Descrizione</th>
			<th>Stato</th>
            <th>Lingua</th>
			<th>IVA</th>
			<th>Prezzo</th>
			<th>Stock</th>
            <th>Link Accesso</th>


		</tr>
		<tr>
			<td><%=product.getIdProdotto()%></td>
			<td><%=product.getNome()%></td>
			<td><%=product.getCategoria() %></td>
			<td><%=product.getDescrizione()%></td>
			<td><%=product.getStato()%></td>
			<td><%=product.getLingua()%></td>
            <td><%=product.getIva()%></td>
			<td><%=product.getPrezzo()%></td>
			<td><%=product.getStock()%></td>
            <td><%=product.getLinkAccesso()%></td>


		</tr>
	</table>
	<%
		}
	%>

    <h2>Insert</h2>
	<form action="product?action=insert" method="post">
		<input type="hidden" name="action" value="insert"> 
		
		<label for="nome">Nome:</label><br> 
		<input name="nome" type="text" maxlength="20" required placeholder="inserisci nome"><br> 
		
        <label for="categoria">Categoria:</label><br>
		<textarea name="categoria" maxlength="100" rows="3" required placeholder="inserisci categoria"></textarea><br>

		<label for="descrizione">Descrizione:</label><br>
		<textarea name="descrizione" maxlength="100" rows="3" required placeholder="inserisci descrizione"></textarea><br>
		
		<label for="stato">Stato:</label><br>
		<label><input type="radio" name="disponibilita" value="true"> Sì</label>
        <label><input type="radio" name="disponibilita" value="false"> No</label><br>

		<label for="lingua">Lingua:</label><br> 
		<textarea name="lingua" maxlength="100" rows="3" required placeholder="inserisci lingua"></textarea><br>

		<label for="IVA">IVA:</label><br> 
		<input name="IVA" type="number" maxlength="20" required placeholder="inserisci l'IVA"><br> 

		<label for="Prezzo">Prezzo:</label><br>
		<input name="prezzo" type="number" min="0" value="0" required><br>
		
        <label for="Stock">Stock:</label><br>
		<input name="stock" type="number" min="0" value="0" required><br>
		
		<label for="LinkAccesso">Link Accesso:</label><br> 
		<textarea name="linkaccesso" maxlength="100" rows="3" required placeholder="inserisci link accesso"></textarea><br>

		<input type="submit" value="Add"><input type="reset" value="Reset">

	</form>
	<h2>Carrello</h2>
	<table border = "1">
		<tr>
			<th>Id</th>
			<th>Nome</th>
			<th>Prezzo</th>
		</tr>
		<%
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		if(carrello != null){
		List<ProductBean> prodotti = carrello.getProdotti();
		for(ProductBean prodotto : prodotti){
		%>
	
		
		<tr>
			<td><%=prodotto.getIdProdotto()%></td>
			<td><%=prodotto.getNome()%></td>
			<td><%=prodotto.getPrezzo()%></td>
		</tr>
		<%} %>
		</table>
		<%}
			%>
	
</body>
</html>


