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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Handle both create and update logic
	    String method = request.getParameter("_method");
	    String lessonTitle = request.getParameter("lessonTitle");
	    String content = request.getParameter("content");
	    String videoUrl = request.getParameter("videoUrl");
	    String courseIdParam = request.getParameter("courseId");
	    String lessonIdParam = request.getParameter("lessonId");

	    // Validate required fields
	    if (lessonTitle == null || content == null || videoUrl == null || courseIdParam == null) {
	        request.setAttribute("errorMessage", "All fields are required. Please fill out the form completely.");
	        request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
	        return;
	    }

	    // Parse courseId
	    int courseId = Integer.parseInt(courseIdParam);

	    // Get the instructorId from session
	    Integer instructorId = (Integer) request.getSession().getAttribute("userId");
	    if (instructorId == null) {
	        request.setAttribute("errorMessage", "Instructor ID is missing. Please log in.");
	        request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
	        return;
	    }

	    // Create a Lesson object
	    Lesson lesson = new Lesson();
	    lesson.setLessonTitle(lessonTitle);
	    lesson.setContent(content);
	    lesson.setVideoUrl(videoUrl);
	    lesson.setCourseId(courseId);

	    // Handle creation or update
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        LessonDao lessonDao = new LessonDao();

	        if ("PUT".equalsIgnoreCase(method) && lessonIdParam != null) {
	            // Update logic
	            int lessonId = Integer.parseInt(lessonIdParam);
	            lesson.setLessonId(lessonId);
	            boolean isUpdated = lessonDao.updateLesson(lesson);

	            if (isUpdated) {
	                response.sendRedirect(request.getContextPath() + "/lesson?courseId=" + courseId);
	            } else {
	                request.setAttribute("errorMessage", "Failed to update lesson. Please try again.");
	                request.getRequestDispatcher("/Views/Lesson/editLesson.jsp").forward(request, response);
	            }
	        } else {
	            // Create logic
	            boolean isAdded = lessonDao.addLesson(lesson);

	            if (isAdded) {
	                response.sendRedirect(request.getContextPath() + "/lesson?courseId=" + courseId);
	            } else {
	                request.setAttribute("errorMessage", "Failed to create lesson. Please try again.");
	                request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	    }
	}

	// DELETE - Delete a lesson
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

	    // Parse the JSON-like string manually to extract lessonId
	    String json = stringBuilder.toString();
	    // Extract numeric lessonId value (removing any non-digit characters)
	    String lessonIdParam = json.replaceAll("[^0-9]", "");

	    if (lessonIdParam != null) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            LessonDao lessonDao = new LessonDao();
	            int lessonId = Integer.parseInt(lessonIdParam);

	            // Try to delete the lesson using the DAO
	            if (lessonDao.deleteLesson(lessonId)) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Lesson deleted successfully");
	            } else {
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().write("Failed to delete lesson");
	            }
	        } catch (SQLException e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Database error occurred");
	        }
	    } else {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("Lesson ID is required");
	    }
	}

}