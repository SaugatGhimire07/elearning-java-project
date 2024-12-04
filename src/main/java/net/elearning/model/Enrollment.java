package net.elearning.model;

import java.sql.Timestamp;

public class Enrollment {
    private int enrollmentId;
    private int courseId;
    private int studentId;
    private Timestamp enrollmentDate;
    private String enrollmentStatus;

    // Default Constructor
    public Enrollment() {
    }

    // Parameterized Constructor
    public Enrollment(int enrollmentId, int courseId, int studentId, Timestamp enrollmentDate, String enrollmentStatus) {
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentStatus = enrollmentStatus;
    }

    // Getters and Setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Timestamp enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", enrollmentDate=" + enrollmentDate +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                '}';
    }
}
