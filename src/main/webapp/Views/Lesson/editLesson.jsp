<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Lesson</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Lesson</h1>
        <form action="${pageContext.request.contextPath}/lesson" method="POST">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="lessonId" value="${lesson.lessonId}">
            <input type="hidden" name="courseId" value="${lesson.courseId}">

            <div class="form-group">
                <label for="lessonTitle">Lesson Title:</label>
                <input type="text" class="form-control" id="lessonTitle" name="lessonTitle" value="${lesson.lessonTitle}" required>
            </div>

            <div class="form-group">
                <label for="content">Content:</label>
                <textarea class="form-control" id="content" name="content" rows="4" required>${lesson.content}</textarea>
            </div>

            <div class="form-group">
                <label for="videoUrl">Video URL:</label>
                <input type="text" class="form-control" id="videoUrl" name="videoUrl" value="${lesson.videoUrl}" required>
            </div>

            <button type="submit" class="btn btn-primary">Update Lesson</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
