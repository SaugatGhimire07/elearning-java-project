package net.elearning.model;

import java.sql.Timestamp;

public class Course {
	private int courseId;
	private String courseTitle;
	private String description;
	private String learningOutcome;
	private String experienceLevel;
	private double price;
	private String coverImageUrl;
	private int instructorId;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	private String instructorName;
	private int categoryId;
	private String categoryName;

	// Constructor
	public Course() {
	}

	public Course(int courseId, String courseTitle, String description, String learningOutcome, String experienceLevel,
			double price, String coverImageUrl, int instructorId, Timestamp createdAt, Timestamp updatedAt) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.description = description;
		this.learningOutcome = learningOutcome;
		this.experienceLevel = experienceLevel;
		this.price = price;
		this.coverImageUrl = coverImageUrl;
		this.instructorId = instructorId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters for categoryId and categoryName
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	// Existing Getters and Setters
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description != null ? description.trim() : null;
	}

	public String getLearningOutcome() {
		return learningOutcome;
	}

	public void setLearningOutcome(String learningOutcome) {
		this.learningOutcome = learningOutcome != null ? learningOutcome.trim() : null;
	}

	public String getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Course{" + "courseId=" + courseId + ", courseTitle='" + courseTitle + '\'' + ", description='"
				+ description + '\'' + ", learningOutcome='" + learningOutcome + '\'' + ", experienceLevel='"
				+ experienceLevel + '\'' + ", price=" + price + ", coverImageUrl='" + coverImageUrl + '\''
				+ ", instructorId=" + instructorId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", categoryId=" + categoryId + ", categoryName='" + categoryName + '\'' + '}';
	}
}
