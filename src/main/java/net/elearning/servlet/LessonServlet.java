package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.elearning.dao.LessonDao;
import net.elearning.model.Lesson;
import net.elearning.dao.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Constructor
	public LessonServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Integer userId = (Integer) session.getAttribute("userId");
			String userType = (String) session.getAttribute("userType");

			// Check if user is logged in and has a valid user type
			if (userId == null || userType == null) {
				// Redirect to login page if session attributes are missing
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			// Retrieve courseId and action from the request
			String courseIdParam = request.getParameter("courseId");
			String action = request.getParameter("action");

			// If the action is to create a lesson, forward to the lesson creation page
			if ("create".equals(action) && courseIdParam != null) {
				request.setAttribute("courseId", courseIdParam);
				request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
				return;
			}

			// In the 'edit' action block of the doGet method
			if ("edit".equals(action) && courseIdParam != null) {
				String lessonIdParam = request.getParameter("lessonId");
				if (lessonIdParam != null) {
					try {
						int lessonId = Integer.parseInt(lessonIdParam);
						// Fetch the lesson details for editing
						try (Connection connection = DatabaseConnection.getConnection()) {
							LessonDao lessonDao = new LessonDao();
							Lesson lesson = lessonDao.getLessonById(lessonId); // Get the lesson object by ID
							if (lesson != null) {
								// Set the lesson object as a request attribute to be accessed in the JSP
								request.setAttribute("lesson", lesson);
								request.setAttribute("courseId", courseIdParam); // You can also pass courseId
								request.getRequestDispatcher("/Views/Lesson/editLesson.jsp").forward(request, response);
							} else {
								response.sendError(HttpServletResponse.SC_NOT_FOUND, "Lesson not found.");
							}
						}
					} catch (NumberFormatException e) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson ID.");
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Lesson ID is required for editing.");
				}
				return;
			}

			// Retrieve lessons for a specific course if courseId is provided
			if (courseIdParam != null) {
				int courseId = Integer.parseInt(courseIdParam);
				try (Connection connection = DatabaseConnection.getConnection()) {
					LessonDao lessonDao = new LessonDao();
					List<Lesson> lessons = lessonDao.getLessonsByCourseId(courseId);

					// Set the lessons and courseId in the request to forward to the lesson list
					// page
					request.setAttribute("lessons", lessons);
					request.setAttribute("courseId", courseId);

					// Forward to the lesson list page
					request.getRequestDispatcher("/Views/Lesson/lessonList.jsp").forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
				}
			} else {
				// If no courseId is provided, return an error message
				response.getWriter().write("Course ID is required");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form parameters
		String lessonTitle = request.getParameter("lessonTitle");
		String content = request.getParameter("content");
		String videoUrl = request.getParameter("videoUrl");
		String courseIdParam = request.getParameter("courseId");

		// Get the instructorId from session (assuming the user is logged in)
		Integer instructorId = (Integer) request.getSession().getAttribute("userId");
		if (instructorId == null) {
			// Handle case when instructorId is not available (user not logged in)
			request.setAttribute("errorMessage", "Instructor ID is missing. Please log in.");
			request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
			return;
		}

		// Validate the required fields
		if (lessonTitle == null || content == null || videoUrl == null || courseIdParam == null) {
			request.setAttribute("errorMessage", "All fields are required. Please fill out the form completely.");
			request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
			return;
		}

		// Parse the courseId
		int courseId = Integer.parseInt(courseIdParam);

		// Create a new Lesson object with the form data
		Lesson newLesson = new Lesson();
		newLesson.setLessonTitle(lessonTitle);
		newLesson.setContent(content);
		newLesson.setVideoUrl(videoUrl);
		newLesson.setCourseId(courseId);

		// Insert the lesson into the database using LessonDao
		try (Connection connection = DatabaseConnection.getConnection()) {
			LessonDao lessonDao = new LessonDao();
			boolean isAdded = lessonDao.addLesson(newLesson);

			// Check if the lesson insertion was successful
			if (isAdded) {
				// Redirect to the lesson list page for the course
				String redirectUrl = request.getContextPath() + "/lesson?courseId=" + courseId;
				response.sendRedirect(redirectUrl);
			} else {
				// Handle failure by redirecting back to createLesson.jsp with an error message
				request.setAttribute("errorMessage", "Failed to create lesson. Please try again.");
				request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			LessonDao lessonDao = new LessonDao();
			String lessonIdParam = request.getParameter("lessonId");
			String courseIdParam = request.getParameter("courseId");
			String lessonTitle = request.getParameter("lessonTitle");
			String content = request.getParameter("content");
			String videoUrl = request.getParameter("videoUrl");

			// Validate that all required parameters are provided
			if (lessonIdParam != null && courseIdParam != null && lessonTitle != null && content != null
					&& videoUrl != null) {
				int lessonId = Integer.parseInt(lessonIdParam);
				int courseId = Integer.parseInt(courseIdParam);

				// Fetch the existing lesson to ensure it exists before updating
				Lesson existingLesson = lessonDao.getLessonById(lessonId);
				if (existingLesson != null && existingLesson.getCourseId() == courseId) {
					// Update the lesson with new values
					existingLesson.setLessonTitle(lessonTitle);
					existingLesson.setContent(content);
					existingLesson.setVideoUrl(videoUrl);

					boolean isUpdated = lessonDao.updateLesson(existingLesson);
					if (isUpdated) {
						// Redirect to the lesson list page for the course
						String redirectUrl = request.getContextPath() + "/lesson?courseId=" + courseId;
						response.sendRedirect(redirectUrl);
					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						response.getWriter().write("Failed to update the lesson. Please try again.");
					}
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.getWriter().write("Lesson not found or mismatch with courseId.");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Missing required parameters");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid lessonId or courseId");
		}
	}

	// DELETE - Delete a lesson
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lessonIdParam = request.getParameter("lessonId");

		if (lessonIdParam != null) {
			try (Connection connection = DatabaseConnection.getConnection()) {
				LessonDao lessonDao = new LessonDao();
				int lessonId = Integer.parseInt(lessonIdParam);
				boolean isDeleted = lessonDao.deleteLesson(lessonId);
				if (isDeleted) {
					response.getWriter().write("Lesson deleted successfully");
				} else {
					response.getWriter().write("Failed to delete lesson");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
			}
		} else {
			response.getWriter().write("Lesson ID is required");
		}
	}
}