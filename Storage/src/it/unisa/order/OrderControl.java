package it.unisa.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unisa.user.UserModelDM;
import it.unisa.user.UserBean;
import it.unisa.product.ProductModelDS;

/**
 * Servlet implementation class OrderControl
 */
@WebServlet("/OrderControl")
public class OrderControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static OrderModelDM model = new OrderModelDM();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if(action != null){
				if(action.equalsIgnoreCase("read")){
					int id = Integer.parseInt(request.getParameter("idFattura"));
					request.removeAttribute("productsFattura");
					request.setAttribute("productsFattura", model.doRetrivePrductsF(id));
				} else if(action.equalsIgnoreCase("print")) {
					UserBean
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
