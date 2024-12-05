package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elearning.dao.CategoryDao;
import net.elearning.model.Category;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ExploreServlet
 */
@WebServlet("/explore")
public class ExploreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExploreServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all categories from the CategoryDao
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAllCategories();

        // Set the list of categories as a request attribute
        request.setAttribute("categories", categories);

        // Forward the request to the explore.jsp page
        request.getRequestDispatcher("/Views/Frontend/explore.jsp").forward(request, response);
    }
}
