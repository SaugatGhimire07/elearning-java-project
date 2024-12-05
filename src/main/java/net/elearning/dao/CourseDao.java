package net.elearning.dao;

import net.elearning.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

	// Constructor to initialize the connection from DatabaseConnection
	public CourseDao() {
	}

	// Insert a new course
    public int insertCourse(Course course) {
        String query = "INSERT INTO Course (course_title, description, learning_outcome, experience_level, price, cover_image_url, instructor_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, course.getCourseTitle());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getLearningOutcome());
            stmt.setString(4, course.getExperienceLevel());
            stmt.setDouble(5, course.getPrice());
            stmt.setString(6, course.getCoverImageUrl());
            stmt.setInt(7, course.getInstructorId());
            stmt.setInt(8, course.getCategoryId());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Retrieve the generated course_id
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the generated courseId
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    public Course getCourseById(int courseId) {
        String query = "SELECT c.*, u.full_name AS instructor_name, ca.category_name " +
                       "FROM Course c " +
                       "JOIN User u ON c.instructor_id = u.user_id " +
                       "JOIN Category ca ON c.category_id = ca.category_id " +
                       "WHERE c.course_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = mapRowToCourse(rs);
                    course.setInstructorName(rs.getString("instructor_name"));
                    course.setCategoryName(rs.getString("category_name")); // Set category name
                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the course is not found
    }


	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		String query = "SELECT * FROM Course";

		// Use try-with-resources for automatic resource management
		try (Connection connection = DatabaseConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				courses.add(mapRowToCourse(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public List<Course> getCoursesByCategoryId(int categoryId) {
	    List<Course> courses = new ArrayList<>();
	    String query = "SELECT c.*, u.full_name AS instructor_name, ca.category_name " +
	                   "FROM Course c " +
	                   "JOIN User u ON c.instructor_id = u.user_id " +
	                   "JOIN Category ca ON c.category_id = ca.category_id " +
	                   "WHERE ca.category_id = ?"; // Filter by category_id

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, categoryId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Course course = mapRowToCourse(rs);
	                course.setInstructorName(rs.getString("instructor_name"));
	                course.setCategoryName(rs.getString("category_name")); // Set category name
	                courses.add(course);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return courses;
	}


	public List<Course> getLastFourCourses() {
	    List<Course> lastFourCourses = new ArrayList<>();
	    String sql = "SELECT c.course_id, c.course_title, c.description, c.price, c.cover_image_url, u.full_name AS instructor_name " +
	                 "FROM Course c " +
	                 "JOIN User u ON c.instructor_id = u.user_id " +
	                 "WHERE u.user_type = 'Instructor' " +
	                 "ORDER BY c.created_at DESC LIMIT 4"; // Example query

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Course course = new Course();
	            course.setCourseId(rs.getInt("course_id"));
	            course.setCourseTitle(rs.getString("course_title"));
	            course.setDescription(rs.getString("description"));
	            course.setPrice(rs.getDouble("price"));
	            course.setCoverImageUrl(rs.getString("cover_image_url"));
	            course.setInstructorName(rs.getString("instructor_name")); // Add instructor name
	            lastFourCourses.add(course);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("courses"+lastFourCourses);
	    return lastFourCourses;
	}
	
	public List<Course> getCoursesByInstructorId(int instructorId) {
	    List<Course> courses = new ArrayList<>();
	    String query = "SELECT * FROM Course WHERE instructor_id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, instructorId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Course course = new Course();
	            course.setCourseId(rs.getInt("course_id"));
	            course.setCourseTitle(rs.getString("course_title"));
	            course.setDescription(rs.getString("description"));
	            course.setLearningOutcome(rs.getString("learning_outcome"));
	            course.setExperienceLevel(rs.getString("experience_level"));
	            course.setPrice(rs.getDouble("price"));
	            course.setCoverImageUrl(rs.getString("cover_image_url"));
	            course.setInstructorId(rs.getInt("instructor_id"));
	            course.setCreatedAt(rs.getTimestamp("created_at"));
	            course.setUpdatedAt(rs.getTimestamp("updated_at"));
	            courses.add(course);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return courses;
	}



	// Update a course
	public boolean updateCourse(Course course) {
		String query = "UPDATE Course SET course_title = ?, description = ?, learning_outcome = ?, "
				+ "experience_level = ?, price = ?, cover_image_url = ?, instructor_id = ?, updated_at = CURRENT_TIMESTAMP WHERE course_id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, course.getCourseTitle());
			stmt.setString(2, course.getDescription());
			stmt.setString(3, course.getLearningOutcome());
			stmt.setString(4, course.getExperienceLevel());
			stmt.setDouble(5, course.getPrice());
			stmt.setString(6, course.getCoverImageUrl());
			stmt.setInt(7, course.getInstructorId());
			stmt.setInt(8, course.getCourseId());

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0; // Return true if update is successful
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Return false if update fails
	}

	// Delete a course
	public boolean deleteCourse(int courseId) {
		String query = "DELETE FROM Course WHERE course_id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, courseId);

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Course with ID " + courseId + " deleted successfully.");
				return true;
			} else {
				System.out.println("No course found with ID " + courseId + ".");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Error while deleting course with ID " + courseId + ": " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private Course mapRowToCourse(ResultSet rs) throws SQLException {
	    int courseId = rs.getInt("course_id");
	    String courseTitle = rs.getString("course_title");
	    String description = rs.getString("description");
	    String learningOutcome = rs.getString("learning_outcome");
	    String experienceLevel = rs.getString("experience_level");
	    double price = rs.getDouble("price");
	    String coverImageUrl = rs.getString("cover_image_url");
	    int instructorId = rs.getInt("instructor_id");
	    Timestamp createdAt = rs.getTimestamp("created_at");
	    Timestamp updatedAt = rs.getTimestamp("updated_at");

	    return new Course(courseId, courseTitle, description, learningOutcome, experienceLevel, price, coverImageUrl,
	            instructorId, createdAt, updatedAt);
	}

}
