<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create New Course</title>

<!-- Bootstrap CSS from CDN -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<h1 class="mb-4">Create New Course</h1>
		<form action="<%=request.getContextPath()%>/course" method="POST"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="courseTitle">Course Title:</label> <input type="text"
					class="form-control" id="courseTitle" name="courseTitle" required>
			</div>

			<div class="form-group">
				<label for="description">Description:</label>
				<textarea class="form-control" id="description" name="description"
					rows="4" required></textarea>
			</div>

			<div class="form-group">
				<label for="learningOutcome">Learning Outcome:</label>
				<textarea class="form-control" id="learningOutcome"
					name="learningOutcome" rows="4" required></textarea>
			</div>

			<div class="form-group">
				<label for="experienceLevel">Experience Level:</label> <select
					class="form-control" id="experienceLevel" name="experienceLevel"
					required>
					<option value="Beginner">Beginner</option>
					<option value="Intermediate">Intermediate</option>
					<option value="Advanced">Advanced</option>
				</select>
			</div>

			<div class="form-group">
				<label for="categoryId">Category:</label>
				<select class="form-control" id="categoryId" name="categoryId" required>
					<c:forEach var="category" items="${categories}">
						<option value="${category.categoryId}">${category.categoryName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<label for="price">Price:</label> <input type="number"
					class="form-control" id="price" name="price" required>
			</div>

			<div class="form-group">
				<label for="coverImageUrl">Cover Image:</label> <input type="file"
					class="form-control-file" id="coverImageUrl" name="coverImageUrl"
					accept="image/*">
			</div>

			<button type="submit" class="btn btn-primary">Create Course</button>
			<a href="<%=request.getContextPath()%>/course" class="btn btn-secondary ml-2">Cancel</a>
		</form>
	</div>

	<!-- Bootstrap JS and dependencies -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
