package net.elearning.model;

import java.sql.Timestamp;

public class Lesson {
    private int lessonId;
    private int courseId;
    private String lessonTitle;
    private String content;
    private String videoUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructor
    public Lesson() {
    }

    public Lesson(int lessonId, int courseId, String lessonTitle, String content, 
                  String videoUrl, Timestamp createdAt, Timestamp updatedAt) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.videoUrl = videoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content != null ? content.trim() : null;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", courseId=" + courseId +
                ", lessonTitle='" + lessonTitle + '\'' +
                ", content='" + content + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
