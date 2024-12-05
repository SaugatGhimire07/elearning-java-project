package net.elearning.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.elearning.dao.LessonDao;
import net.elearning.model.Lesson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class LessonPlayerServlet
 */
@WebServlet("/lessonplayer")
public class LessonPlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LessonPlayerServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseId = request.getParameter("courseId");
        String lessonId = request.getParameter("lessonId");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (courseId != null && userId != null) {
            try {
                LessonDao lessonDao = new LessonDao();
                // Fetch all lessons for the course and user
                List<Lesson> lessons = lessonDao.getLessonsForUserAndCourse(userId, Integer.parseInt(courseId));
                request.setAttribute("lessons", lessons);

                // Determine the selected lesson
                Lesson selectedLesson = null;
                if (lessonId != null) {
                    int lessonIdInt = Integer.parseInt(lessonId);
                    for (Lesson lesson : lessons) {
                        if (lesson.getLessonId() == lessonIdInt) {
                            selectedLesson = lesson;
                            break;
                        }
                    }
                }
                // If no specific lessonId is provided, default to the first lesson
                if (selectedLesson == null && !lessons.isEmpty()) {
                    selectedLesson = lessons.get(0);
                }

                // Pass the selected lesson to the JSP
                request.setAttribute("selectedLesson", selectedLesson);
                request.setAttribute("courseId", courseId);

                // Forward to JSP
                request.getRequestDispatcher("/Views/Frontend/lessonPlayer.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the lessons.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing courseId or userId");
        }
    }

}
