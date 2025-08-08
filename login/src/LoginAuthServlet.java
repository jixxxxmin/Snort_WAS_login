import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet(name = "LoginAuthServlet", urlPatterns = {"/login/auth"})
public class LoginAuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        
        HttpSession s = req.getSession(false);

        if (s != null) {
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain; charset=UTF-8");
            resp.getWriter().write("ok");

            response.getWriter().println("You have session");
        }
        else {
            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().println("You don't have session");
        }
    }
}
