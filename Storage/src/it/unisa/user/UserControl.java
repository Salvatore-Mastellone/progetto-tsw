package it.unisa.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;


/**
 * Servlet implementation class UserControl
 */
@WebServlet("/UserControl")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static UserModel model = new UserModelDM();

    public UserControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action"); //vede che tio di azione deve essere eseguita
		
		try {
			if(action != null) {
				// in caso di login
				if(action.equalsIgnoreCase("login")) {
					String email = request.getParameter("email");	//preleva l'email dalla richiesta
					String pwd = request.getParameter("password");	//preleva la password dalla richiesta
					UserBean u = model.doRetrieveByKey(email,0);		//restituisce un user se esiste con quella email
					//solo se l'user esiste e la pass
					if(u.getEmail() != null && BCrypt.checkpw(pwd, u.getPassword())){
						request.getSession().setAttribute("admin", false);
			        	request.getSession().setAttribute("email", u.getEmail()); //la usiamo anche per cambiare i dati dell'utente
			        	request.getSession().setAttribute("nome", u.getNome());
			        	request.getSession().setAttribute("cognome", u.getCognome());
			        	request.getSession().setAttribute("indirizzo", u.getIndirizzo());
			        	request.getSession().setAttribute("città", u.getCitta());
			        	request.getSession().setAttribute("provincia", u.getProvincia());
			        	request.getSession().setAttribute("Cap", u.getCap());
						response.sendRedirect("protectedUser/PageUtente.jsp");
					}
					else 
						response.sendRedirect("FailLogin.jsp");
				}
				else if(action.equalsIgnoreCase("registration")) {
					String email = request.getParameter("email");
					UserBean tmp = model.doRetrieveByKey(email,0);

					if(tmp!= null && tmp.getEmail().equals(email)) { //se l'email già c'è l'utente viene mandato al Login
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
			} else if(action.equalsIgnoreCase("modifica")) {
				
				String originalEmail = (String)request.getSession().getAttribute("email"); //cerchiamo l'utente nel db per cambiare i dati
				UserBean oldUsr = model.doRetrieveByKey(originalEmail,0);

				
				String email = request.getParameter("email"); //email del form
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String indirizzo = request.getParameter("indirizzo");
				String citta = request.getParameter("citta");
				String provincia = request.getParameter("provincia"); 
				String cap = request.getParameter("cap");

				
				
				UserBean usr; 
				
				String checkValue = request.getParameter("check");
				
				//verifica se l'utente vuole anche cambiare password
				if(checkValue != null && checkValue.equals("true")) {
					String oldPassword = request.getParameter("oldPass");
					
					//se la pass vecchia è stata messa correttamente si procede al cambio password
					if(BCrypt.checkpw(oldPassword, oldUsr.getPassword())) {
						String newPassword = request.getParameter("newPass");
						String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
						usr = new UserBean(nome, cognome, email, citta, provincia, hashedPassword, indirizzo, Integer.parseInt(cap));
						model.doUpdate(originalEmail, usr);
					} else { //se la password vecchia non coincide
						System.out.println("Password errata o mancante"); //lo implementiamo con qualche pagina di errore
					}
					
				} else { //se l'utente non vuole cambiare password
					
					//cambiamo solo gli altri dati
					usr = new UserBean(nome, cognome, email, citta, provincia, null, indirizzo, Integer.parseInt(cap));
					model.doUpdate(originalEmail, usr);
					
				}
				
				response.sendRedirect("Login.jsp");
				
			 }
			}
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
	}
