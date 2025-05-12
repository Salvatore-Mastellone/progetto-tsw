package it.unisa;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ProductModel model = new ProductModelDS();;
	
    public ProductControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("read")) {
					int id = Integer.parseInt(request.getParameter("id"));
					request.removeAttribute("product");
					request.setAttribute("product", model.doRetrieveByKey(id));
				} else if (action.equalsIgnoreCase("delete")) {
					int id = Integer.parseInt(request.getParameter("id"));
					model.doDelete(id);
				} else if (action.equalsIgnoreCase("insert")) {
					String name = request.getParameter("nome");
					String categoria = request.getParameter("categoria");
					String description = request.getParameter("descrizione");
					boolean stato;
					if(request.getParameter("disponibilita").equalsIgnoreCase("false"))
						stato = false;
					else
						stato = true;
					String lingua = request.getParameter("lingua");
					int iva = Integer.parseInt(request.getParameter("IVA"));
					float price = Float.parseFloat(request.getParameter("prezzo"));
					int quantity = Integer.parseInt(request.getParameter("stock"));
					String link = request.getParameter("linkaccesso");
					
					ProductBean bean = new ProductBean();
					bean.setNome(name);
					bean.setCategoria(categoria);
					bean.setDescrizione(description);
					bean.setStato(stato);
					bean.setLingua(lingua);
					bean.setIva(iva);
					bean.setPrezzo(price);
					bean.setStock(quantity);
					bean.setLinkAccesso(link);
					model.doSave(bean);
				} else if(action.equalsIgnoreCase("add")) {
					int id = Integer.parseInt(request.getParameter("id"));
					HttpSession sessione = request.getSession();
					Carrello carrello = (Carrello) sessione.getAttribute("carrello");
					if(carrello == null)
						carrello = new Carrello();
					ProductBean prodotto = model.doRetrieveByKey(id);
					carrello.addCarrello(prodotto);
					sessione.setAttribute("carrello", carrello);
					
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		String sort = request.getParameter("sort");

		try {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
