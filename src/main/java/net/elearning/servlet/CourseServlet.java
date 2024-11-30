package net.elearning.servlet;

import net.elearning.dao.CourseDao;
import net.elearning.model.Course;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            System.out.println("Logged in userId: " + userId);
            String userType = (String) session.getAttribute("userType");

            if (userId == null || userType == null) {
                // Redirect to login if session attributes are missing
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

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

                        if (course != null && course.getInstructorId() == userId) {
                            // Set the course as a request attribute and forward to the edit page
                            request.setAttribute("course", course);
                            request.getRequestDispatcher("/Views/Course/editCourse.jsp").forward(request, response);
                            return;
                        } else if (course != null) {
                            // If the instructor is not the owner of the course
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to edit this course.");
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
            } else if ("listCourses".equals(action)) {
                // Fetch courses based on user type
                List<Course> courses;
                if ("Instructor".equals(userType)) {
                    // Get courses created by the instructor
                    courses = courseDao.getCoursesByInstructorId(userId);
                } else if ("Student".equals(userType)) {
                    // Students can see all available courses
                    courses = courseDao.getAllCourses();
                } else {
                    courses = null; // In case of an unexpected user type
                }

                System.out.println("Courses fetched: " + (courses != null ? courses.size() : "No courses found"));

                // Set the list of courses as a request attribute
                request.setAttribute("courses", courses);

                // Forward the request to the JSP page to display the list of courses
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Course/courseList.jsp");
                dispatcher.forward(request, response);
            } else {
                // Default: Fetch courses based on user type
                List<Course> courses;
                if ("Instructor".equals(userType)) {
                    // Get courses created by the instructor
                    courses = courseDao.getCoursesByInstructorId(userId);
                } else if ("Student".equals(userType)) {
                    // Students can see all available courses
                    courses = courseDao.getAllCourses();
                } else {
                    courses = null; // In case of an unexpected user type
                }

                System.out.println("Courses fetched (default action): " + (courses != null ? courses.size() : "No courses found"));

                // Set the list of courses as a request attribute
                request.setAttribute("courses", courses);

                // Forward the request to the JSP page to display the list of courses
                request.getRequestDispatcher("/Views/Course/courseList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle multipart form data (file upload)
        String courseTitle = request.getParameter("courseTitle");
        String description = request.getParameter("description");
        String learningOutcome = request.getParameter("learningOutcome");
        String experienceLevel = request.getParameter("experienceLevel");
        double price = Double.parseDouble(request.getParameter("price"));

        // Get the instructorId from session (assuming the user is logged in)
        Integer instructorId = (Integer) request.getSession().getAttribute("userId");
        if (instructorId == null) {
            // Handle case when instructorId is not available (user not logged in)
            request.setAttribute("errorMessage", "Instructor ID is missing. Please log in.");
            request.getRequestDispatcher("/Views/Course/createCourse.jsp").forward(request, response);
            return;
        }

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
        // Parse course ID from the request parameter
        String courseIdParam = request.getParameter("courseId");
        
        // Check if courseId is missing or invalid
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing or invalid courseId");
            return;
        }

        try {
            // Parse the courseId to integer
            int courseId = Integer.parseInt(courseIdParam);

            // Retrieve updated form data from the request
            String courseTitle = request.getParameter("courseTitle");
            String description = request.getParameter("description");
            String learningOutcome = request.getParameter("learningOutcome");
            String experienceLevel = request.getParameter("experienceLevel");
            double price = Double.parseDouble(request.getParameter("price"));

            // Optionally get the instructorId from the request (if needed for security checks)
            int instructorId = Integer.parseInt(request.getParameter("instructorId"));

            // Handle file upload (if a new cover image is provided)
            Part coverImagePart = request.getPart("coverImageUrl");
            String coverImageUrl = null;

            if (coverImagePart != null && coverImagePart.getSize() > 0) {
                coverImageUrl = handleFileUpload(coverImagePart); // Upload the new file and get the path
            }

            // Fetch the existing course from the database to ensure it exists
            Course existingCourse = courseDao.getCourseById(courseId);
            if (existingCourse == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Course with ID " + courseId + " not found");
                return;
            }

            // Ensure the instructorId matches the existing course instructorId
            if (existingCourse.getInstructorId() != instructorId) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You are not authorized to update this course");
                return;
            }

            // Update the fields of the existing course
            existingCourse.setCourseTitle(courseTitle);
            existingCourse.setDescription(description);
            existingCourse.setLearningOutcome(learningOutcome);
            existingCourse.setExperienceLevel(experienceLevel);
            existingCourse.setPrice(price);
            
            // If a new cover image is uploaded, replace the existing one
            if (coverImageUrl != null) {
                existingCourse.setCoverImageUrl(coverImageUrl);
            }

            // Call the DAO to update the course in the database
            boolean isUpdated = courseDao.updateCourse(existingCourse);

            // If updated successfully, redirect to the course list page
            if (isUpdated) {
                String redirectUrl = request.getContextPath() + "/course?action=listCourses";
                response.sendRedirect(redirectUrl);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update the course. Please try again.");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid courseId or numerical values provided");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred: " + e.getMessage());
            e.printStackTrace();  // Log the full error stack for debugging
        }
    }

    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     // Read the JSON body of the request
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        // Parse the JSON-like string manually to extract courseId
        String json = stringBuilder.toString();

        // Find the courseId from the body (assuming format like: {"courseId": "123"})
        String courseIdParam = json.replaceAll("[^0-9]", ""); // Extract the numeric part of the courseId


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
