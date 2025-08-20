import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




// @WebServlet(name = "CreateSubmenuServlet", urlPatterns = {"/admin/create_submenu"})
public class CreateSubmenuServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String DB_URL = System.getenv("DB_URL");
        String DB_USER = System.getenv("DB_USER");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");

        String action = request.getParameter("action");
        String submenu_id = request.getParameter("submenu_id");
        String submenu_name = request.getParameter("submenu_name");
        String descript = request.getParameter("descript");
        String menu_id = request.getParameter("menu_id");

        String query = null;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        if ("add".equals(action)) {
            if (submenu_name != null && !submenu_name.trim().isEmpty() &&
                menu_id != null && !menu_id.trim().isEmpty()) {
                query = "INSERT INTO SubMenu (submenu_name, descript, menu_id) VALUES (?, ?, ?)";
            }
        } 
        else if ("update".equals(action)) {
            if (submenu_id != null && !submenu_id.trim().isEmpty() &&
                submenu_name != null && !submenu_name.trim().isEmpty() &&
                menu_id != null && !menu_id.trim().isEmpty()) {
                query = "UPDATE SubMenu SET submenu_name = ?, descript = ?, menu_id = ? WHERE submenu_id = ?";
            }
        }
        else {
            response.reset();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Not action");
            return;
        }

        if (query == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("query가 생성되지 않았습니다.");
            return;
        }

        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            if ("add".equals(action)) {
                stmt.setString(1, submenu_name);
                stmt.setString(2, descript);
                stmt.setString(3, menu_id);
            }
            else if ("update".equals(action)) {
                stmt.setString(1, submenu_id);
                stmt.setString(2, descript);
                stmt.setString(3, menu_id);
                stmt.setString(4, submenu_id);
            }

            int rs = stmt.executeUpdate();

            if (rs > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Submenu 생성 실패");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("submenu 생성 불가 : " + e.getMessage());
        }
    }
}
