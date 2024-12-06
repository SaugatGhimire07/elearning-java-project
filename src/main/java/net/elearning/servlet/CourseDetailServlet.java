package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        
        // Retrieve userId (which will serve as studentId)
        Integer studentId = (Integer) session.getAttribute("userId");
        
        // Check if studentId is available in session
        if (studentId == null) {
            // If no userId in session, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        if (courseIdParam != null) {
            int courseId = Integer.parseInt(courseIdParam);
            CourseDao courseDao = new CourseDao();
            
            // Retrieve course details
            Course course = courseDao.getCourseById(courseId);
            
            if (course != null) {
                // Check if the student is enrolled in the course
                boolean isEnrolled = courseDao.isStudentEnrolledInCourse(studentId, courseId);
                
                // Retrieve the lesson count for the course
                int lessonCount = courseDao.countLessons(courseId);
                
                // Set attributes for the JSP page
                request.setAttribute("course", course);
                request.setAttribute("lessonCount", lessonCount);
                request.setAttribute("isEnrolled", isEnrolled); // Add the enrollment status
                
                // Forward to the JSP page
                request.getRequestDispatcher("/Views/Frontend/courseDetail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID");
        }
    }
}
