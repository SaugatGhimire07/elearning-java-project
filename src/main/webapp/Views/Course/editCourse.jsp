<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Course</title>

    <!-- Bootstrap CSS from CDN -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Course</h1>
        <form action="${pageContext.request.contextPath}/course" method="POST" enctype="multipart/form-data">
            <!-- Hidden field to indicate PUT request -->
            <input type="hidden" name="_method" value="PUT">

            <!-- Hidden courseId field to identify the course -->
            <input type="hidden" name="courseId" value="${course.courseId}">

            <div class="form-group">
                <label for="courseTitle">Course Title:</label>
                <input type="text" class="form-control" id="courseTitle" name="courseTitle" value="${course.courseTitle}" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" name="description" rows="4" required>${course.description}</textarea>
            </div>

            <div class="form-group">
                <label for="learningOutcome">Learning Outcome:</label>
                <textarea class="form-control" id="learningOutcome" name="learningOutcome" rows="4" required>${course.learningOutcome}</textarea>
            </div>

            <div class="form-group">
                <label for="experienceLevel">Experience Level:</label>
                <select class="form-control" id="experienceLevel" name="experienceLevel" required>
                    <option value="Beginner" ${course.experienceLevel == 'Beginner' ? 'selected' : ''}>Beginner</option>
                    <option value="Intermediate" ${course.experienceLevel == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                    <option value="Advanced" ${course.experienceLevel == 'Advanced' ? 'selected' : ''}>Advanced</option>
                </select>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" class="form-control" id="price" name="price" value="${course.price}" required>
            </div>

            <div class="form-group">
                <label for="coverImageUrl">Cover Image:</label>
                <input type="file" class="form-control-file" id="coverImageUrl" name="coverImageUrl" accept="image/*"> <br>

                <!-- Display the image if the coverImageUrl is not empty -->
                <c:choose>
                    <c:when test="${not empty course.coverImageUrl}">
                        <img src="${pageContext.request.contextPath}/${course.coverImageUrl}" alt="Course Cover" class="img-fluid" style="max-width: 200px; max-height: 200px;">
                    </c:when>
                    <c:otherwise>
                        <p>No cover image available.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <button type="submit" class="btn btn-primary">Update Course</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
