import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>GET method success!</h2>");
            out.println("</body></html>");
        }
        catch (Exception e){
            throw new ServletException(e);
        }
    }


}
