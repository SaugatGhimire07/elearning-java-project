package net.elearning.servlet;

import net.elearning.dao.LessonDao;
import net.elearning.model.Lesson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@WebServlet("/lesson")
@MultipartConfig
public class LessonServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LessonDao lessonDao;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the LessonDao for database interaction
        lessonDao = new LessonDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle multipart form data (file upload)
        String lessonTitle = request.getParameter("lessonTitle");
        String content = request.getParameter("content");
        String videoUrl = request.getParameter("videoUrl");
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        // Create a new Lesson object with the form data
        Lesson newLesson = new Lesson();
        newLesson.setLessonTitle(lessonTitle);
        newLesson.setContent(content);
        newLesson.setVideoUrl(videoUrl);
        newLesson.setCourseId(courseId);

        // Insert the lesson into the database using LessonDao
        int generatedLessonId = lessonDao.insertLesson(newLesson);
        System.out.println("Generated Lesson ID: " + generatedLessonId);

        // Check if the lesson insertion was successful
        if (generatedLessonId != -1) {
            // Redirect to the lesson list page
            String redirectUrl = request.getContextPath() + "/lesson?action=list&courseId=" + courseId;
            response.sendRedirect(redirectUrl);
        } else {
            // Handle failure by redirecting back to the create lesson page with an error message
            request.setAttribute("errorMessage", "Failed to create lesson. Please try again.");
            request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            int courseId = Integer.parseInt(request.getParameter("courseId"));

            if ("create".equals(action)) {
                // Forward to the page where the user can create a new lesson
                request.setAttribute("courseId", courseId);
            	request.getRequestDispatcher("/Views/Lesson/createLesson.jsp").forward(request, response);

            } else if ("edit".equals(action)) {
                // Handle the edit action
                String lessonIdParam = request.getParameter("lessonId");

                if (lessonIdParam != null && !lessonIdParam.isEmpty()) {
                    try {
                        int lessonId = Integer.parseInt(lessonIdParam);

                        // Fetch the lesson details from the database
                        Lesson lesson = lessonDao.getLessonById(lessonId);

                        if (lesson != null) {
                            // Set the lesson as a request attribute and forward to the edit page
                            request.setAttribute("lesson", lesson);
                            request.getRequestDispatcher("/Views/Lesson/editLesson.jsp").forward(request, response);
                            return;
                        } else {
                            // If the lesson is not found, show an error message
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Lesson not found.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid lessonId format
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson ID.");
                        return;
                    }
                } else {
                    // If lessonId is missing
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lesson ID.");
                    return;
                }
            } else if ("list".equals(action)) {
                // List lessons for a specific course
                List<Lesson> lessons = lessonDao.getLessonsByCourseId(courseId);
                request.setAttribute("lessons", lessons);
                request.setAttribute("courseId", courseId);
                request.getRequestDispatcher("/Views/Lesson/lessonList.jsp").forward(request, response);
            } else {
                // Default: Display the list of lessons for the course
                List<Lesson> lessons = lessonDao.getLessonsByCourseId(courseId);
                request.setAttribute("lessons", lessons);
                request.setAttribute("courseId", courseId);
                request.getRequestDispatcher("/Views/Lesson/lessonList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    private String handleFileUpload(Part filePart) throws IOException {
        String uploadDir = getServletContext().getRealPath("/uploads");
        File dir = new File(uploadDir);

        // Ensure the directory exists
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Directory created: " + uploadDir);
        }

        // Get the file name
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String filePath = uploadDir + File.separator + fileName;

        // Save the uploaded file to the server
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File uploaded to: " + filePath);
        }

        return "/uploads/" + fileName;  // Return relative path
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonIdParam = request.getParameter("lessonId");

        if (lessonIdParam == null || lessonIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing lessonId");
            return;
        }

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            // Retrieve updated form data from the request
            String lessonTitle = request.getParameter("lessonTitle");
            String content = request.getParameter("content");
            String videoUrl = request.getParameter("videoUrl");
            int courseId = Integer.parseInt(request.getParameter("courseId"));

            // Handle file upload (if any)
            Part lessonVideoPart = request.getPart("lessonVideoUrl");
            String lessonVideoUrl = null;

            if (lessonVideoPart != null && lessonVideoPart.getSize() > 0) {
                lessonVideoUrl = handleFileUpload(lessonVideoPart); // Upload the new file and get the path
            }

            // Fetch the existing lesson from the database to ensure it exists
            Lesson existingLesson = lessonDao.getLessonById(lessonId);
            if (existingLesson == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Lesson not found");
                return;
            }

            // Update the fields of the existing lesson
            existingLesson.setLessonTitle(lessonTitle);
            existingLesson.setContent(content);
            existingLesson.setVideoUrl(videoUrl);
            existingLesson.setCourseId(courseId);

            // If a new lesson video is uploaded, replace the existing one
            if (lessonVideoUrl != null) {
                existingLesson.setVideoUrl(lessonVideoUrl);
            }

            // Call the DAO to update the lesson in the database
            boolean isUpdated = lessonDao.updateLesson(existingLesson);

            // If updated successfully, redirect to the lesson list page
            if (isUpdated) {
                String redirectUrl = request.getContextPath() + "/lesson?action=list&courseId=" + courseId;
                response.sendRedirect(redirectUrl);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update lesson");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid lessonId or numerical values");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonIdParam = request.getParameter("lessonId");

        if (lessonIdParam == null || lessonIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing lessonId");
            return;
        }

        try {
            int lessonId = Integer.parseInt(lessonIdParam);
            boolean isDeleted = lessonDao.deleteLesson(lessonId);

            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Lesson deleted successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Failed to delete lesson");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid lessonId");
        }
    }
}
