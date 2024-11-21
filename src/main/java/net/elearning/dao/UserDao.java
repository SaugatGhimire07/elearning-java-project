package net.elearning.dao;

import net.elearning.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // Constructor
    public UserDao() {
    }

    // Insert a new user
    public int insertUser(User user) {
        String query = "INSERT INTO User (full_name, user_type, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUserType());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Return the generated user_id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    // Get a user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM User WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user not found
    }
    
 // Get a user by email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM User WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user not found
    }


    // Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update a user
    public boolean updateUser(User user) {
        String query = "UPDATE User SET full_name = ?, user_type = ?, email = ?, password = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUserType());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if update fails
    }

    // Delete a user
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM User WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Return true if deletion is successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if deletion fails
    }

    // Helper method to map a ResultSet row to a User object
    private User mapRowToUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String fullName = rs.getString("full_name");
        String userType = rs.getString("user_type");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String createdAt = rs.getString("created_at");
        String updatedAt = rs.getString("updated_at");

        return new User(userId, fullName, userType, email, password, createdAt, updatedAt);
    }
}
