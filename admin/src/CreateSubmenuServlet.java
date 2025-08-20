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

        String submenu_name = request.getParameter("submenu_name");
        String descript = request.getParameter("descript");
        String menu_id = request.getParameter("menu_id");

        String query;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        if (submenu_name != null && !submenu_name.trim().isEmpty() && menu_id != null && !menu_id.trim().isEmpty()) {
            query = "INSERT INTO SubMenu (submenu_name, descript, menu_id) VALUES (?, ?, ?)";
        }
        else {
            response.reset();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, submenu_name);
            stmt.setString(2, descript);
            stmt.setString(3, menu_id);

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
