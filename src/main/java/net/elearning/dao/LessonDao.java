package net.elearning.dao;

import net.elearning.model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

	private Connection connection;

	// Constructor to initialize the connection
	public LessonDao(Connection connection) {
		this.connection = connection;
	}

	// CREATE - Add a new lesson
	public boolean addLesson(Lesson lesson) {
		String sql = "INSERT INTO Lesson (course_id, lesson_title, content, video_url) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, lesson.getCourseId());
			statement.setString(2, lesson.getLessonTitle());
			statement.setString(3, lesson.getContent());
			statement.setString(4, lesson.getVideoUrl());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// READ - Get a lesson by ID
	public Lesson getLessonById(int lessonId) {
		String sql = "SELECT * FROM Lesson WHERE lesson_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, lessonId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return mapResultSetToLesson(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// READ - Get all lessons for a course
	public List<Lesson> getLessonsByCourseId(int courseId) {
		List<Lesson> lessons = new ArrayList<>();
		String sql = "SELECT * FROM Lesson WHERE course_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, courseId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				lessons.add(mapResultSetToLesson(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lessons;
	}

	public boolean updateLesson(Lesson lesson) {
		// Check if the lesson exists before updating
		if (!lessonExists(lesson.getLessonId(), lesson.getCourseId())) {
			return false; // If lesson does not exist, return false
		}

		String sql = "UPDATE Lesson SET course_id = ?, lesson_title = ?, content = ?, video_url = ?, updated_at = CURRENT_TIMESTAMP WHERE lesson_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, lesson.getCourseId());
			statement.setString(2, lesson.getLessonTitle());
			statement.setString(3, lesson.getContent());
			statement.setString(4, lesson.getVideoUrl());
			statement.setInt(5, lesson.getLessonId());

			// If the update was successful (affected rows > 0), return true
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Helper method to check if the lesson exists
	private boolean lessonExists(int lessonId, int courseId) {
		String checkSql = "SELECT COUNT(*) FROM Lesson WHERE lesson_id = ? AND course_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(checkSql)) {
			statement.setInt(1, lessonId);
			statement.setInt(2, courseId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0; // Return true if the lesson exists
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// DELETE - Delete a lesson
	public boolean deleteLesson(int lessonId) {
		String sql = "DELETE FROM Lesson WHERE lesson_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, lessonId);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Helper method to map the result set to a Lesson object
	private Lesson mapResultSetToLesson(ResultSet resultSet) throws SQLException {
		int lessonId = resultSet.getInt("lesson_id");
		int courseId = resultSet.getInt("course_id");
		String lessonTitle = resultSet.getString("lesson_title");
		String content = resultSet.getString("content");
		String videoUrl = resultSet.getString("video_url");
		Timestamp createdAt = resultSet.getTimestamp("created_at");
		Timestamp updatedAt = resultSet.getTimestamp("updated_at");
		return new Lesson(lessonId, courseId, lessonTitle, content, videoUrl, createdAt, updatedAt);
	}
}
