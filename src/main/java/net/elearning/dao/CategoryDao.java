package net.elearning.dao;

import net.elearning.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

	// Constructor to initialize the connection from DatabaseConnection
	public CategoryDao() {
	}

	// Insert a new category
	public int insertCategory(Category category) {
		String query = "INSERT INTO Category (category_name) VALUES (?)";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, category.getCategoryName());

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				// Retrieve the generated category_id
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1); // Return the generated categoryId
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Return -1 if insertion fails
	}

	public Category getCategoryById(int categoryId) {
	    Category category = null;
	    String query = "SELECT * FROM Category WHERE category_id = ?";
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, categoryId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                category = new Category();
	                category.setCategoryId(rs.getInt("category_id"));
	                category.setCategoryName(rs.getString("category_name"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return category;
	}


	public List<Category> getAllCategories() {
	    List<Category> categories = new ArrayList<>();
	    String query = "SELECT * FROM Category";

	    try (Connection connection = DatabaseConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            categories.add(mapRowToCategory(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return categories;
	}

	// Update a category
	public boolean updateCategory(Category category) {
		String query = "UPDATE Category SET category_name = ? WHERE category_id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, category.getCategoryName());
			stmt.setInt(2, category.getCategoryId());

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0; // Return true if update is successful
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Return false if update fails
	}

	// Delete a category
	public boolean deleteCategory(int categoryId) {
		String query = "DELETE FROM Category WHERE category_id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, categoryId);

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Category with ID " + categoryId + " deleted successfully.");
				return true;
			} else {
				System.out.println("No category found with ID " + categoryId + ".");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Error while deleting category with ID " + categoryId + ": " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private Category mapRowToCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setCategoryName(rs.getString("category_name"));
		return category;
	}

}
