package it.unisa;


import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	
	static UserModel model = new UserModelDM();
	 
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String param = request.getParameter("log"); //verifica se dobbiamo fare il login
		
		if(param.equals("true")) { //il login è settato a true
			
			//ottiene email e password dal form
			String user = request.getParameter("email");
			String plainPass = request.getParameter("password");
			
			request.getSession().setAttribute("login", true);  //abbiamo settato un attributo nella sessione
			
			try {
				UserBean u = model.doRetrieveByKey(user);
				String pass = u.getPassword();
		        boolean matches = BCrypt.checkpw(plainPass, pass);
		        
		        //salva i dati dell'utente durante la sessione
		        if (matches) {
		        	request.getSession().setAttribute("admin", false);
		        	request.getSession().setAttribute("email", u.getEmail());
		        	request.getSession().setAttribute("nome", u.getNome());
		        	request.getSession().setAttribute("cognome", u.getCognome());
		        	request.getSession().setAttribute("indirizzo", u.getIndirizzo());
		        	request.getSession().setAttribute("città", u.getCitta());
		        	request.getSession().setAttribute("provincia", u.getProvincia());
		        	request.getSession().setAttribute("Cap", u.getCap());
					response.sendRedirect("PageUtente.jsp");
		        } else {
					response.sendRedirect("FailLogin.jsp");
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		} else if(param.equals("false")) { //se non dobbiamo fare il login vediamo se è possibile la registrazione
	
			try {				
				String email = request.getParameter("email");
				UserBean tmp = model.doRetrieveByKey(email);

				if(tmp.getEmail().equals(email)) { //se l'email già c'è l'utente viene mandato al Login
					response.sendRedirect("ErrorPage.jsp");
				} else {	
				//settiamo l'utente da salvare...		
				UserBean user = new UserBean();
				user.setCap(Integer.parseInt(request.getParameter("cap")));
				user.setCitta(request.getParameter("citta"));
				user.setCognome(request.getParameter("cognome"));
				user.setEmail(email);
				user.setIndirizzo(request.getParameter("indirizzo"));
				user.setNome(request.getParameter("nome"));
				String password = request.getParameter("password");
				String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
				user.setPassword(hashedPassword);
				user.setProvincia(request.getParameter("provincia"));
				model.doSave(user);
				response.sendRedirect("SuccRegistration.jsp");
				
				}
			}catch(Exception E) {
				System.out.println(E.getMessage());
			}
			
		}
	
	}

}
