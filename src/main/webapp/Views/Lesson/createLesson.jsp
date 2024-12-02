<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Lesson</title>

    <!-- Bootstrap CSS from CDN -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Create New Lesson</h1>
        <form action="<%=request.getContextPath()%>/lesson" method="POST">
            <input type="hidden" name="courseId" value="<%= request.getParameter("courseId") %>" />

            <div class="form-group">
                <label for="lessonTitle">Lesson Title:</label>
                <input type="text" class="form-control" id="lessonTitle" name="lessonTitle" required>
            </div>

            <div class="form-group">
                <label for="content">Content:</label>
                <textarea class="form-control" id="content" name="content" rows="4" required></textarea>
            </div>

            <div class="form-group">
                <label for="videoUrl">Video URL:</label>
                <input type="url" class="form-control" id="videoUrl" name="videoUrl" required>
            </div>

            <button type="submit" class="btn btn-primary">Create Lesson</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
