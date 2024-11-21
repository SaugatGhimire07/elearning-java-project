<%@ page contentType="text/html;charset=UTF-8"
	import="net.elearning.model.Course" language="java"%>
<!DOCTYPE html>

<%
Course course = (Course) request.getAttribute("course"); // Deserialize (retrieve) the Course object
%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Course</title>

<!-- Bootstrap CSS from CDN -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<h1 class="mb-4">Edit Course</h1>
		<form
			action="${pageContext.request.contextPath}/course"
			method="POST" enctype="multipart/form-data">
			<input type="hidden" name="_method" value="PUT">
			<!-- Hidden field to simulate PUT request -->

			<div class="form-group">
				<label for="courseTitle">Course Title:</label> <input type="text"
					class="form-control" id="courseTitle" name="courseTitle"
					value="<%=course.getCourseTitle()%>" required>
			</div>

			<div class="form-group">
				<label for="description">Description:</label>
				<textarea class="form-control" id="description" name="description"
					rows="4" required><%=course.getDescription()%></textarea>
			</div>

			<div class="form-group">
				<label for="learningOutcome">Learning Outcome:</label>
				<textarea class="form-control" id="learningOutcome"
					name="learningOutcome" rows="4" required><%=course.getLearningOutcome()%></textarea>
			</div>

			<div class="form-group">
				<label for="experienceLevel">Experience Level:</label> <select
					class="form-control" id="experienceLevel" name="experienceLevel"
					required>
					<option value="Beginner"
						<%="Beginner".equals(course.getExperienceLevel()) ? "selected" : ""%>>Beginner</option>
					<option value="Intermediate"
						<%="Intermediate".equals(course.getExperienceLevel()) ? "selected" : ""%>>Intermediate</option>
					<option value="Advanced"
						<%="Advanced".equals(course.getExperienceLevel()) ? "selected" : ""%>>Advanced</option>
				</select>
			</div>


			<div class="form-group">
				<label for="price">Price:</label> <input type="number"
					class="form-control" id="price" name="price"
					value="<%=course.getPrice()%>" required>
			</div>

			<div class="form-group">
			    <label for="coverImageUrl">Cover Image:</label>
			    <input type="file" class="form-control-file" id="coverImageUrl" name="coverImageUrl" accept="image/*">
			    <br>
			
			    <!-- Display the image if the coverImageUrl is not empty -->
			    <% if (course.getCoverImageUrl() != null && !course.getCoverImageUrl().isEmpty()) { %>
			        <!-- Display the image from the file path stored in course.getCoverImageUrl() -->
			        <img src="<%= course.getCoverImageUrl() %>" alt="Course Cover" class="img-fluid" style="max-width: 200px; max-height: 200px;">
			    <% } else { %>
			        <p>No cover image available.</p>
			    <% } %>
			</div>


			<div class="form-group">
				<label for="instructorId">Instructor ID:</label> <input
					type="number" class="form-control" id="instructorId"
					name="instructorId" value="<%=course.getInstructorId()%>" required>
			</div>

			<button type="submit" class="btn btn-primary">Update Course</button>
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
