package net.elearning.servlet;

import net.elearning.dao.CategoryDao;
import net.elearning.model.Category;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CategoryDao categoryDao;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the CategoryDao for database interaction
        categoryDao = new CategoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            String userType = (String) session.getAttribute("userType");

            if (userId == null || userType == null) {
                // Redirect to login if session attributes are missing
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String action = request.getParameter("action");

            if ("create".equals(action)) {
                // Forward to the page where the user can create a new category
                request.getRequestDispatcher("/Views/Category/createCategory.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                // Handle the edit action
                String categoryIdParam = request.getParameter("categoryId");

                if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                    try {
                        int categoryId = Integer.parseInt(categoryIdParam);

                        // Fetch the category details from the database
                        Category category = categoryDao.getCategoryById(categoryId);

                        if (category != null) {
                            // Set the category as a request attribute and forward to the edit page
                            request.setAttribute("category", category);
                            request.getRequestDispatcher("/Views/Category/editCategory.jsp").forward(request, response);
                            return;
                        } else {
                            // If the category is not found, show an error message
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid categoryId format
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");
                        return;
                    }
                } else {
                    // If categoryId is missing
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing category ID.");
                    return;
                }
            } else if ("listCategories".equals(action)) {
                // Fetch all categories
                List<Category> categories = categoryDao.getAllCategories();

                System.out.println("Categories fetched: " + (categories != null ? categories.size() : "No categories found"));

                // Set the list of categories as a request attribute
                request.setAttribute("categories", categories);

                // Forward the request to the JSP page to display the list of categories
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Category/categoryList.jsp");
                dispatcher.forward(request, response);
            } else {
                // Default: Fetch all categories
                List<Category> categories = categoryDao.getAllCategories();

                System.out.println(
                        "Categories fetched (default action): " + (categories != null ? categories.size() : "No categories found"));

                // Set the list of categories as a request attribute
                request.setAttribute("categories", categories);

                // Forward the request to the JSP page to display the list of categories
                request.getRequestDispatcher("/Views/Category/categoryList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle category creation or updating
        String categoryName = request.getParameter("categoryName");

        // Get the categoryId from request to handle updates
        String categoryIdParam = request.getParameter("categoryId");
        Category category;

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            // Category ID is provided, update the existing category
            int categoryId = Integer.parseInt(categoryIdParam);

            // Retrieve the existing category from the database
            category = categoryDao.getCategoryById(categoryId);
            if (category == null) {
                request.setAttribute("errorMessage", "Category not found.");
                request.getRequestDispatcher("/Views/Category/createCategory.jsp").forward(request, response);
                return;
            }

            // Update category properties
            category.setCategoryName(categoryName);

            // Update the category in the database
            boolean updated = categoryDao.updateCategory(category);
            if (updated) {
                response.sendRedirect(request.getContextPath() + "/category?action=listCategories");
            } else {
                request.setAttribute("errorMessage", "Failed to update the category.");
                request.getRequestDispatcher("/Views/Category/createCategory.jsp").forward(request, response);
            }

        } else {
            // If there is no categoryId, create a new category
            category = new Category();
            category.setCategoryName(categoryName);

            // Insert the new category into the database
            int generatedCategoryId = categoryDao.insertCategory(category);
            if (generatedCategoryId != -1) {
                response.sendRedirect(request.getContextPath() + "/category?action=listCategories");
            } else {
                request.setAttribute("errorMessage", "Failed to create the category. Please try again.");
                request.getRequestDispatcher("/Views/Category/createCategory.jsp").forward(request, response);
            }
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read the JSON body of the request
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        // Parse the JSON-like string manually to extract categoryId
        String json = stringBuilder.toString();
        String categoryIdParam = json.replaceAll("[^0-9]", "");

        if (categoryIdParam == null || categoryIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing categoryId");
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdParam);
            boolean isDeleted = categoryDao.deleteCategory(categoryId);

            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Category deleted successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Failed to delete category");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid categoryId");
        }
    }
}
