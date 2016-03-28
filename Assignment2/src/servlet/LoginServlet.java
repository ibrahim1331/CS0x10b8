package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import service.UserService;

/**
 * Servlet implementation class login
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	response.getWriter().append("Get Served at: ").append(request.getContextPath());
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	response.getWriter().append("Post Served at: ").append(request.getContextPath());
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
		String action = request.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("login")) {
                this.loginUser(request, response);
            }
        }else{
        	response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("  <title>Login Page</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login Page</h1>");
                out.println("<form action='login' method='POST'>");
                out.println("<input name='action' type='hidden' value='login' />");
                out.println("  <p>Email: <input type='text' name='email' /></p>");
                out.println("  <p>Password: <input type='password' name='password' /></p>");
                out.println("  <p><input type='submit' value='Login' /></p>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            } finally { 
                out.close();
            }
        }
    }
	
    private void loginUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>User (Retrieve)</h1>");
            out.println("<div style='width:600px'>");
            out.println("<fieldset>");
            out.println("<legend>User List</legend>");
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (email != null && !email.equalsIgnoreCase("") && 
            		password != null && !password.equalsIgnoreCase("")) {
	            
            	boolean valid = userService.authenticate(email, password);
            	
            	if(valid){
            		User user = userService.getUser(email, password);
		            out.println("<div><table style='width:100%'>");
		            out.println("<thead>");
		            out.println("<th align='left'>User ID</th><th align='left'>First Name</th><th align='left'>Last Name</th><th align='left'>Email</th>");
		            out.println("</thead>");
		            out.println("<tbody>");
		            out.println("<tr>");
	                out.println("<td>" + user.getUserId() + "</td>");
	                out.println("<td>" + this.htmlEncode(user.getFirstName()) + "</td>");
	                out.println("<td>" + this.htmlEncode(user.getLastName()) + "</td>");
	                out.println("<td>" + user.getEmail() + "</td>");
	                out.println("</tr>");
		            out.println("</tbody>");
		            out.println("</table></div>");
            	}else{
            		out.println("<h1>Invalid Credentials</h1>");
            	}
            }else{
	            out.println("<h1>Invalid Credentials</h1>");
            }
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    
    private String htmlEncode(String s) {

        StringBuffer sb = new StringBuffer(s.length() * 2);

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if ((ch >= '?' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == ' ') || (ch == '\n')) {
                sb.append(ch);
            }
            else {
                switch(ch) {
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '\'':
                        sb.append("&#039;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    default:
                        sb.append("&#");
                        sb.append(new Integer(ch).toString());
                        sb.append(';');
                }
            }
        }

        return sb.toString();
    }
}