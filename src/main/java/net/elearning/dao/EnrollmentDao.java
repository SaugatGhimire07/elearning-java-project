package net.elearning.dao;

import net.elearning.model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDao {

	private Connection connection;

	public EnrollmentDao() throws SQLException {
		this.connection = DatabaseConnection.getConnection(); // Get connection from the DatabaseConnection class
	}

	public boolean addEnrollment(Enrollment enrollment) {
		String sql = "INSERT INTO Enrollment (course_id, student_id, enrollment_date, enrollment_status) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, enrollment.getCourseId());
			statement.setInt(2, enrollment.getStudentId());
			statement.setTimestamp(3, enrollment.getEnrollmentDate());
			statement.setString(4, enrollment.getEnrollmentStatus());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Enrollment> getLastTwoEnrollmentsWithCourseName(int studentId) {
	    List<Enrollment> enrollments = new ArrayList<>();
	    String sql = "SELECT e.*, c.course_title, c.cover_image_url, u.full_name AS instructor_name " +
	                 "FROM Enrollment e " +
	                 "JOIN Course c ON e.course_id = c.course_id " +
	                 "JOIN User u ON c.instructor_id = u.user_id " +
	                 "WHERE e.student_id = ? " +
	                 "ORDER BY e.enrollment_date DESC " +
	                 "LIMIT 2";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, studentId);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            // Map enrollment details
	            Enrollment enrollment = mapResultSetToEnrollment(resultSet);
	            
	            // Get course details from the result set
	            String courseTitle = resultSet.getString("course_title");
	            String coverImageUrl = resultSet.getString("cover_image_url");
	            String instructorName = resultSet.getString("instructor_name"); // Get instructor's name
	            
	            // Set the course and instructor details
	            enrollment.setCourseTitle(courseTitle);
	            enrollment.setCoverImageUrl(coverImageUrl);
	            
	            // Assuming you have a method in Enrollment class to set the instructor's name
	            enrollment.setInstructorName(instructorName);
	            
	            enrollments.add(enrollment);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return enrollments;
	}

	public List<Enrollment> getAllEnrollmentsWithCourseName(int studentId) {
	    List<Enrollment> enrollments = new ArrayList<>();
	    String sql = "SELECT e.*, c.course_title, c.cover_image_url, u.full_name AS instructor_name " +
	                 "FROM Enrollment e " +
	                 "JOIN Course c ON e.course_id = c.course_id " +
	                 "JOIN User u ON c.instructor_id = u.user_id " +
	                 "WHERE e.student_id = ? " +
	                 "ORDER BY e.enrollment_date DESC ";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, studentId);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            // Map enrollment details
	            Enrollment enrollment = mapResultSetToEnrollment(resultSet);
	            
	            // Get course details from the result set
	            String courseTitle = resultSet.getString("course_title");
	            String coverImageUrl = resultSet.getString("cover_image_url");
	            String instructorName = resultSet.getString("instructor_name");
	            
	            // Set the course and instructor details
	            enrollment.setCourseTitle(courseTitle);
	            enrollment.setCoverImageUrl(coverImageUrl);
	            
	            enrollment.setInstructorName(instructorName);
	            
	            enrollments.add(enrollment);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return enrollments;
	}

	// Helper method to map the result set to an Enrollment object
	private Enrollment mapResultSetToEnrollment(ResultSet resultSet) throws SQLException {
		int enrollmentId = resultSet.getInt("enrollment_id");
		int courseId = resultSet.getInt("course_id");
		int studentId = resultSet.getInt("student_id");
		Timestamp enrollmentDate = resultSet.getTimestamp("enrollment_date");
		String enrollmentStatus = resultSet.getString("enrollment_status");
		return new Enrollment(enrollmentId, courseId, studentId, enrollmentDate, enrollmentStatus);
	}
}
