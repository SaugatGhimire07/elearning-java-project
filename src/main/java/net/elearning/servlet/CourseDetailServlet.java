package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elearning.dao.CourseDao;
import net.elearning.model.Course;

import java.io.IOException;

@WebServlet("/courseDetail")
public class CourseDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CourseDetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdParam = request.getParameter("courseId");
        
        if (courseIdParam != null) {
            int courseId = Integer.parseInt(courseIdParam);
            CourseDao courseDao = new CourseDao();
            Course course = courseDao.getCourseById(courseId);
            
            if (course != null) {
                request.setAttribute("course", course);
                request.getRequestDispatcher("/Views/Frontend/courseDetail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID");
        }
    }
}
