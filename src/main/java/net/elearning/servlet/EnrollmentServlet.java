package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.elearning.dao.EnrollmentDao;
import net.elearning.model.Enrollment;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/enrollment")
public class EnrollmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Constructor
	public EnrollmentServlet() {
		super();
	}

	// Process POST request when the form is submitted
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get courseId from the request parameters
		int courseId = Integer.parseInt(request.getParameter("courseId"));

		// Get the studentId from session
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		// Debugging: Print the userId and courseId to verify
		System.out.println("userId from session: " + userId);
		System.out.println("courseId from request: " + courseId);

		// If userId is not found in session, redirect to login page or show an error
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/Views/User/login.jsp"); // Redirect to login page
			return;
		}

		// Set enrollment status and current timestamp
		String enrollmentStatus = "in progress";
		Timestamp enrollmentDate = new Timestamp(System.currentTimeMillis());

		// Create an Enrollment object
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseId(courseId);
		enrollment.setStudentId(userId);
		enrollment.setEnrollmentDate(enrollmentDate);
		enrollment.setEnrollmentStatus(enrollmentStatus);

		// Create an EnrollmentDao object to interact with the database
		try {
			EnrollmentDao enrollmentDao = new EnrollmentDao();
			boolean success = enrollmentDao.addEnrollment(enrollment);

			if (success) {
				request.setAttribute("enrollmentSuccess", true);
				request.setAttribute("courseId", courseId);
				response.sendRedirect(request.getContextPath() + "/courseDetail?courseId=" + courseId);
			} else {
				// If enrollment fails, forward to an error page or show error message
				request.setAttribute("error", "Enrollment failed. Please try again.");
				request.getRequestDispatcher("/Views/Frontend/courseDetail.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Send error message if there's an exception
			response.sendRedirect(request.getContextPath() + "/Views/Frontend/errorPage.jsp");
		}
	}

}
