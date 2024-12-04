package net.elearning.dao;

import net.elearning.model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDao {

    private Connection connection;

    public EnrollmentDao() throws SQLException {
        this.connection = DatabaseConnection.getConnection();  // Get connection from the DatabaseConnection class
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

    // READ - Get an enrollment by ID
    public Enrollment getEnrollmentById(int enrollmentId) {
        String sql = "SELECT * FROM Enrollment WHERE enrollment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, enrollmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToEnrollment(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Get all enrollments for a course
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollment WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                enrollments.add(mapResultSetToEnrollment(resultSet));
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
