package net.elearning.model;

import java.sql.Timestamp;

public class Lesson {

    private int lessonId;
    private String lessonTitle;
    private String content;
    private String videoUrl;
    private int courseId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Lesson() {
    	
    }
    // Constructor to initialize Lesson object with all fields, including createdAt and updatedAt
    public Lesson(int lessonId, String lessonTitle, String content, String videoUrl, int courseId, Timestamp createdAt, Timestamp updatedAt) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.videoUrl = videoUrl;
        this.courseId = courseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter and Setter methods for all fields
    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
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
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
                ", lessonTitle='" + lessonTitle + '\'' +
                ", content='" + content + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", courseId=" + courseId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
