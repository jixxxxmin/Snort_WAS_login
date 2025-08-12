import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();
            response.setContentType("text/html; charset=UTF-8");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Hello World</title>");
            out.println("<style>");
            out.println("html, body { height: 100%; margin: 0; overflow: hidden; }");
            out.println("body {");
            out.println("  display: flex;");
            out.println("  justify-content: center;");
            out.println("  align-items: center;");
            out.println("  font-family: 'Comic Sans MS', cursive, sans-serif;");
            out.println("  background: linear-gradient(270deg, red, orange, yellow, green, blue, indigo, violet);");
            out.println("  background-size: 2000% 2000%;");
            out.println("  animation: bgmove 10s linear infinite;");
            out.println("}");
            out.println("h1 {");
            out.println("  font-size: 8rem;");
            out.println("  font-weight: bold;");
            out.println("  background: linear-gradient(270deg, red, orange, yellow, green, blue, indigo, violet);");
            out.println("  background-size: 1400% 1400%;");
            out.println("  -webkit-background-clip: text;");
            out.println("  -webkit-text-fill-color: transparent;");
            out.println("  filter: brightness(1.8) contrast(2) saturate(2.5);");
            out.println("  text-shadow: 0 0 10px white, 0 0 20px white, 0 0 40px red, 0 0 80px yellow, 0 0 120px blue, 0 0 160px magenta;");
            out.println("  animation: rainbow 2s linear infinite, spin 6s linear infinite, pulse 1.5s ease-in-out infinite;");
            out.println("}");
            out.println("@keyframes bgmove {");
            out.println("  0% { background-position: 0% 50%; }");
            out.println("  50% { background-position: 100% 50%; }");
            out.println("  100% { background-position: 0% 50%; }");
            out.println("}");
            out.println("@keyframes rainbow {");
            out.println("  0% { background-position: 0% 50%; }");
            out.println("  50% { background-position: 100% 50%; }");
            out.println("  100% { background-position: 0% 50%; }");
            out.println("}");
            out.println("@keyframes spin {");
            out.println("  0% { transform: rotate(0deg); }");
            out.println("  100% { transform: rotate(360deg); }");
            out.println("}");
            out.println("@keyframes pulse {");
            out.println("  0%, 100% { transform: scale(1); }");
            out.println("  50% { transform: scale(1.4); }");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Hello World</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e){
            throw new ServletException(e);
        }
    }


}
