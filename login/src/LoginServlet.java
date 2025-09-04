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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




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
        
        String query = "SELECT salt, password FROM accounts WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                
                
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {oldSession.invalidate();};

                if (rs.next()) {
                    String hash = rs.getString("password");
                    String salt = rs.getString("salt");

                    String hash_salt = generateSHA256Hash(pw + salt);

                    if (hash.equals(hash_salt)) {
                        HttpSession session = request.getSession(true);
                        session.setMaxInactiveInterval(3600);
                        session.setAttribute("id", id);

                        response.sendRedirect("/admin");
                    }
                    else {
                        LoginFail(request, response);
                    }
                }
                else {
                    LoginFail(request, response);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                response.getWriter().println("invalid id or pw : " + e.getMessage());
            }
        }             
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("server error: " + e.getMessage());
        }
    }

    private void LoginFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession oldSession = request.getSession(false);
        
        Cookie jsid = new Cookie("JSESSIONID", "");
        jsid.setMaxAge(0);
        jsid.setPath("/");
        response.addCookie(jsid);

        response.sendRedirect("?login_failed=1");
    }

    private String generateSHA256Hash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
