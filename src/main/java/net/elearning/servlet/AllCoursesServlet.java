package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elearning.dao.CourseDao;
import net.elearning.model.Course;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class AllCoursesServlet
 */
@WebServlet("/allCourses")
public class AllCoursesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllCoursesServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create an instance of the DAO
        CourseDao courseDao = new CourseDao();

        // Retrieve the list of all courses
        List<Course> courses = courseDao.getAllCourses();

        // Add the courses list to the request attributes
        request.setAttribute("courses", courses);

        // Forward the request to the JSP
        request.getRequestDispatcher("/Views/Frontend/allCourses.jsp").forward(request, response);
    }
}
