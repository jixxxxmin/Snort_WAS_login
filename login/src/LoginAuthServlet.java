import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet(name = "LoginAuthServlet", urlPatterns = {"/login/auth"})
public class LoginAuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        
        HttpSession s = request.getSession(false);

        if (s != null) {
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/plain; charset=UTF-8");

            //response.getWriter().println("You have session");
        }
        else {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            //response.getWriter().println("You don't have session");
        }
    }
}
