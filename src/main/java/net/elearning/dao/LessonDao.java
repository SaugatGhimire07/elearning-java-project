package net.elearning.dao;

import net.elearning.model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

    // Constructor to initialize the connection from DatabaseConnection
    public LessonDao() {
    }

    // Insert a new lesson
    public int insertLesson(Lesson lesson) {
        String query = "INSERT INTO Lesson (lesson_title, content, video_url, course_id, created_at, updated_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, lesson.getLessonTitle());
            stmt.setString(2, lesson.getContent());
            stmt.setString(3, lesson.getVideoUrl());
            stmt.setInt(4, lesson.getCourseId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the generated lesson_id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    // Select a lesson by ID
    public Lesson getLessonById(int lessonId) {
        String query = "SELECT * FROM Lesson WHERE lesson_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lessonId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLesson(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the lesson is not found
    }

    // Get all lessons for a specific course
    public List<Lesson> getLessonsByCourseId(int courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT * FROM Lesson WHERE course_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lessons.add(mapRowToLesson(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    // Update a lesson
    public boolean updateLesson(Lesson lesson) {
        String query = "UPDATE Lesson SET lesson_title = ?, content = ?, video_url = ?, course_id = ?, updated_at = CURRENT_TIMESTAMP WHERE lesson_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, lesson.getLessonTitle());
            stmt.setString(2, lesson.getContent());
            stmt.setString(3, lesson.getVideoUrl());
            stmt.setInt(4, lesson.getCourseId());
            stmt.setInt(5, lesson.getLessonId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if update fails
    }

    // Delete a lesson
    public boolean deleteLesson(int lessonId) {
        String query = "DELETE FROM Lesson WHERE lesson_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lessonId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Lesson with ID " + lessonId + " deleted successfully.");
                return true;
            } else {
                System.out.println("No lesson found with ID " + lessonId + ".");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting lesson with ID " + lessonId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map a ResultSet row to a Lesson object
    private Lesson mapRowToLesson(ResultSet rs) throws SQLException {
        int lessonId = rs.getInt("lesson_id");
        String lessonTitle = rs.getString("lesson_title");
        String content = rs.getString("content");
        String videoUrl = rs.getString("video_url");
        int courseId = rs.getInt("course_id");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Lesson(lessonId, lessonTitle, content, videoUrl, courseId, createdAt, updatedAt);
    }
}
