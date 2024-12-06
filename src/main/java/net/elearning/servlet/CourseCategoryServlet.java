package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elearning.dao.CategoryDao;
import net.elearning.dao.CourseDao;
import net.elearning.model.Category;
import net.elearning.model.Course;

import java.io.IOException;
import java.util.List;

@WebServlet("/courseByCategory")
public class CourseCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseCategoryServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		// Fetch courses by category ID
		CourseDao courseDao = new CourseDao();
		List<Course> courses = courseDao.getCoursesByCategoryId(categoryId);

		// Fetch category name by ID
		CategoryDao categoryDao = new CategoryDao();
		Category category = categoryDao.getCategoryById(categoryId);

		// Set attributes for the JSP
		request.setAttribute("courses", courses);
		request.setAttribute("categoryName", category.getCategoryName());

		// Forward the request to the JSP
		request.getRequestDispatcher("/Views/Frontend/courseByCategory.jsp").forward(request, response);
	}
}
