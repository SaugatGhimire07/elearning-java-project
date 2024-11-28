<%@ page contentType="text/html;charset=UTF-8" language="java" import="net.elearning.model.Lesson" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Lesson</title>
    
    <!-- Bootstrap CSS from CDN -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Lesson</h1>

        <%
            Lesson lesson = (Lesson) request.getAttribute("lesson");
            if (lesson == null) {
        %>
            <div class="alert alert-danger" role="alert">
                Lesson details not found. Please try again.
            </div>
        <%
            } else {
        %>

        <form action="<%= request.getContextPath() %>/lesson" method="POST">
            <!-- Hidden fields for lesson ID and course ID -->
            <input type="hidden" name="lessonId" value="<%= lesson.getLessonId() %>">
            <input type="hidden" name="courseId" value="<%= lesson.getCourseId() %>">

            <div class="form-group">
                <label for="lessonTitle">Lesson Title:</label>
                <input type="text" class="form-control" id="lessonTitle" name="lessonTitle" value="<%= lesson.getLessonTitle() %>" required>
            </div>

            <div class="form-group">
                <label for="content">Content:</label>
                <textarea class="form-control" id="content" name="content" rows="4" required><%= lesson.getContent() %></textarea>
            </div>

            <div class="form-group">
                <label for="videoUrl">YouTube Link:</label>
                <input type="url" class="form-control" id="videoUrl" name="videoUrl" value="<%= lesson.getVideoUrl() %>">
            </div>

            <div class="form-group">
                <label for="createdAt">Created At:</label>
                <input type="text" class="form-control" id="createdAt" value="<%= lesson.getCreatedAt() %>" disabled>
            </div>

            <div class="form-group">
                <label for="updatedAt">Updated At:</label>
                <input type="text" class="form-control" id="updatedAt" value="<%= lesson.getUpdatedAt() %>" disabled>
            </div>

            <button type="submit" class="btn btn-primary">Update Lesson</button>
            <a href="lesson?action=list&courseId=<%= lesson.getCourseId() %>" class="btn btn-secondary">Cancel</a>
        </form>

        <%
            }
        %>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
