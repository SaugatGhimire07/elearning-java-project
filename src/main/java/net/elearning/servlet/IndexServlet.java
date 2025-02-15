package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.elearning.dao.CourseDao;
import net.elearning.dao.EnrollmentDao;
import net.elearning.model.Course;
import net.elearning.model.Enrollment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the userId from the session (check if logged in)
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        try {
            // Fetch the last 4 courses using CourseDao
            CourseDao courseDao = new CourseDao();
            List<Course> lastFourCourses = courseDao.getLastFourCourses();

            // Initialize a list for last two enrollments (null if not logged in)
            List<Enrollment> lastTwoEnrollments = null;

            // If user is logged in, fetch the last 2 enrollments
            if (userId != null) {
                EnrollmentDao enrollmentDao = new EnrollmentDao();
                lastTwoEnrollments = enrollmentDao.getLastTwoEnrollmentsWithCourseName(userId);
            }

            // Set the courses and optionally the last two enrollments as request attributes
            request.setAttribute("lastFourCourses", lastFourCourses);
            request.setAttribute("lastTwoEnrollments", lastTwoEnrollments);

            // Forward to the index.jsp page to display the data
            request.getRequestDispatcher("/Views/Frontend/index.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, forward to an error page or show a custom error message
            request.setAttribute("error", "An error occurred while fetching data.");
            request.getRequestDispatcher("/Views/Error/error.jsp").forward(request, response);
        }
    }
}
