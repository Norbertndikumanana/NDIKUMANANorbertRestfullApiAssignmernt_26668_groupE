package search;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");
        if (query != null && !query.trim().isEmpty()) {
            // Redirect to Google search
            response.sendRedirect("https://www.google.com/search?q=" + java.net.URLEncoder.encode(query, "UTF-8"));
        } else {
            // If no query, redirect back to the search page
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }
}