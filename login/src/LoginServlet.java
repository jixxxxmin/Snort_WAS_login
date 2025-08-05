import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                String id = request.getParameter("id");
                String pw = request.getParameter("password");

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.6.10:3306/users?serverTimezone=Asia/Seoul", "login", "QQww11@@");
                     PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM account WHERE id = ? AND password = ?")){
                        stmt.setString(1, id);
                        stmt.setString(2, pw);
                        boolean success = stmt.executeQuery().next();
                        response.getWriter().println(success ? "login success" : "login failed");
                }
                     
                catch (Exception e) {
                    e.printStackTrace();
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().println("server error: " + e.getMessage());
                }
            }
}
