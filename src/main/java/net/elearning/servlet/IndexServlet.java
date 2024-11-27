package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.elearning.dao.CourseDao;  // Import CourseDao
import net.elearning.model.Course;  // Import Course

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseDao courseDao = new CourseDao();
        List<Course> lastFourCourses = courseDao.getLastFourCourses();
        
        
        request.setAttribute("lastFourCourses", lastFourCourses);  // Set courses in the request attribute
        request.getRequestDispatcher("/Views/Frontend/index.jsp").forward(request, response);
    }
}
