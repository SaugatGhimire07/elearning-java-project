package net.elearning.servlet;

import net.elearning.dao.CourseDao;
import net.elearning.model.Course;

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
import java.util.Collection;
import java.util.List;

@WebServlet("/course")
@MultipartConfig
public class CourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CourseDao courseDao;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the CourseDao for database interaction
        courseDao = new CourseDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle multipart form data (file upload)
        String courseTitle = request.getParameter("courseTitle");
        String description = request.getParameter("description");
        String learningOutcome = request.getParameter("learningOutcome");
        String experienceLevel = request.getParameter("experienceLevel");
        double price = Double.parseDouble(request.getParameter("price"));
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));

        // Get the file part (cover image) from the multipart request
        Part coverImagePart = request.getPart("coverImageUrl");
        String coverImageUrl = null;
        
        // Handle file upload if a cover image is provided
        if (coverImagePart != null && coverImagePart.getSize() > 0) {
            coverImageUrl = handleFileUpload(coverImagePart);  // Get the uploaded file's path
        }

        // Create a new Course object with the form data
        Course newCourse = new Course();
        newCourse.setCourseTitle(courseTitle);
        newCourse.setDescription(description);
        newCourse.setLearningOutcome(learningOutcome);
        newCourse.setExperienceLevel(experienceLevel);
        newCourse.setPrice(price);
        newCourse.setCoverImageUrl(coverImageUrl);  // Set the uploaded image path
        newCourse.setInstructorId(instructorId);

        // Insert the course into the database using CourseDao
        int generatedCourseId = courseDao.insertCourse(newCourse);
        System.out.println("Generated Course ID: " + generatedCourseId);

        // Check if the course insertion was successful
        if (generatedCourseId != -1) {
            // Redirect to a success page or list of courses
        	String redirectUrl = request.getContextPath() + "/course";
            response.sendRedirect(redirectUrl);

        } else {
            // Handle failure by redirecting back to createCourse.jsp with an error message
            request.setAttribute("errorMessage", "Failed to create course. Please try again.");
            request.getRequestDispatcher("/Views/Course/createCourse.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("create".equals(action)) {
                // Forward to the page where the user can create a new course
                request.getRequestDispatcher("/Views/Course/createCourse.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                // Handle the edit action
                String courseIdParam = request.getParameter("courseId");

                if (courseIdParam != null && !courseIdParam.isEmpty()) {
                    try {
                        int courseId = Integer.parseInt(courseIdParam);

                        // Fetch the course details from the database
                        Course course = courseDao.getCourseById(courseId);

                        if (course != null) {
                            // Set the course as a request attribute and forward to the edit page
                            request.setAttribute("course", course);
                            request.getRequestDispatcher("/Views/Course/editCourse.jsp").forward(request, response);
                            return;
                        } else {
                            // If the course is not found, show an error message
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid courseId format
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID.");
                        return;
                    }
                } else {
                    // If courseId is missing
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course ID.");
                    return;
                }
            } else {
                // Default: Display the list of courses
                List<Course> courses = courseDao.getAllCourses();
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/Views/Course/courseList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    
    private String handleFileUpload(Part filePart) throws IOException {
        // Get the project root directory (where the project is located)
        String projectPath = "/Users/saugatghimire/eclipse-workspace/elearning";


        // Define the upload directory under 'src/main/webapp/uploads'
        String uploadDirectory = projectPath + "/src/main/webapp/uploads";

        // Ensure the upload directory exists
        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
            dir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Get the file name from the uploaded file
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Create the file object in the 'uploads' directory
        File file = new File(uploadDirectory, fileName);

        // Save the uploaded file
        filePart.write(file.getAbsolutePath());

        // Return the file path (can be used for accessing or storing the file)
        return file.getAbsolutePath();  // Can be relative or absolute, depending on how you plan to use it
    }

    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse course ID from the request parameter
        String courseIdParam = request.getParameter("courseId");
        System.out.println("----"+ courseIdParam);

        
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing courseId");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdParam);

            // Retrieve updated form data from the request
            String courseTitle = request.getParameter("courseTitle");
            String description = request.getParameter("description");
            String learningOutcome = request.getParameter("learningOutcome");
            String experienceLevel = request.getParameter("experienceLevel");
            double price = Double.parseDouble(request.getParameter("price"));
            int instructorId = Integer.parseInt(request.getParameter("instructorId"));
            System.out.println("----"+ courseTitle);

            // Handle file upload (if any)
            Part coverImagePart = request.getPart("coverImageUrl");
            String coverImageUrl = null;

            if (coverImagePart != null && coverImagePart.getSize() > 0) {
                coverImageUrl = handleFileUpload(coverImagePart); // Upload the new file and get the path
            }

            // Fetch the existing course from the database to ensure it exists
            Course existingCourse = courseDao.getCourseById(courseId);
            if (existingCourse == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Course not found");
                return;
            }

            // Update the fields of the existing course
            existingCourse.setCourseTitle(courseTitle);
            existingCourse.setDescription(description);
            existingCourse.setLearningOutcome(learningOutcome);
            existingCourse.setExperienceLevel(experienceLevel);
            existingCourse.setPrice(price);
            existingCourse.setInstructorId(instructorId);

            // If a new cover image is uploaded, replace the existing one
            if (coverImageUrl != null) {
                existingCourse.setCoverImageUrl(coverImageUrl);
            }

            // Call the DAO to update the course in the database
            boolean isUpdated = courseDao.updateCourse(existingCourse);

            // If updated successfully, redirect to the createCourse.jsp page
            if (isUpdated) {
                String redirectUrl = request.getContextPath() + "/Views/Course/createCourse.jsp";
                response.sendRedirect(redirectUrl);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update course");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid courseId or numerical values");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdParam = request.getParameter("courseId");

        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing courseId");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdParam);
            boolean isDeleted = courseDao.deleteCourse(courseId);

            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Course deleted successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Failed to delete course");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid courseId");
        }
    }


}
