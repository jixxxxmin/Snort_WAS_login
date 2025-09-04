import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




// @WebServlet(name = "LoginServlet", urlPatterns = {"/login/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8"); 

        String id = request.getParameter("id");
        String pw = request.getParameter("password");

        String DB_URL = System.getenv("DB_URL");
        String DB_USER = System.getenv("DB_USER");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM accounts WHERE id=? AND password=SHA2(CONCAT(?, salt), 256);")){
                stmt.setString(1, id);
                stmt.setString(2, pw);
                boolean success = stmt.executeQuery().next();
                        
                if (success) {
                    HttpSession oldSession = request.getSession(false);
                    if (oldSession != null) {oldSession.invalidate();};
                        
                    HttpSession session = request.getSession(true);
                    session.setMaxInactiveInterval(3600);
                    session.setAttribute("id", id);
                        
                    response.sendRedirect("/admin");
                }
                else {
                    HttpSession oldSession = request.getSession(false);
                    if (oldSession != null) {oldSession.invalidate();}

                    Cookie jsid = new Cookie("JSESSIONID", "");
                    jsid.setMaxAge(0);
                    jsid.setPath("/");
                    response.addCookie(jsid);
                    
                    response.sendRedirect("?login_failed=1");
                }
            }
                     
        catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("server error: " + e.getMessage());
        }
    }
}
