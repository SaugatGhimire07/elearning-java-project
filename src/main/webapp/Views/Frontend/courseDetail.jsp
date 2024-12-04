<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Course Detail</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/courseDetail.css" />
</head>
<body>
	<div class="main">
		<div class="top-banner">
			<h1 class="banner-heading">${course.courseTitle}</h1>
			<p class="banner-content">${course.description}</p>
			<div class="Instructor-wrapper">
				<span class="Instructor">Instructor:</span> <span class="Inst-name">${course.instructorName}</span>
			</div>

			<p class="Price">$ ${course.price} | Full Lifetime Access</p>

			<form action="${pageContext.request.contextPath}/enrollment"
				method="post">
				<input type="hidden" name="courseId" value="${course.courseId}" />
				<input type="hidden" name="studentId" value="${student.id}" />
				<button type="submit" class="Enroll">Enroll Now</button>
			</form>
			
			<img class="shape"
				src="${pageContext.request.contextPath}/Views/Images/Shape.png" />
		</div>
		<div class="lower-banner">
			<div class="lower-banner-item">
				<h3>10 Lessons</h3>
				<p>Get in-depth knowledge of a subject</p>
			</div>
			<div class="lower-banner-item">
				<h3>${course.experienceLevel}Level</h3>
				<p>No prior experience required</p>
			</div>
			<div class="lower-banner-item">
				<h3>Flexible Schedule</h3>
				<p>Learn at your own pace</p>
			</div>
		</div>
		<div class="learn-section">
			<h2 class="learn-heading">What you'll learn</h2>
			<div class="learn-content">
				<ul class="learn-column">
					<li>&#10003 Grasp the fundamentals of prompt engineering,
						including the principles of effective prompt design and the role
						of context in AI responses.</li>
					<li>&#10003 Acquire skills to craft precise prompts that
						enhance AI output quality, utilizing techniques for clarity and
						specificity.</li>
				</ul>
				<ul class="learn-column">
					<li>&#10003 Gain proficiency in analyzing and refining prompts
						through iteration, understanding how to adapt prompts for various
						AI models and applications.</li>
					<li>&#10003 Get hands-on experience with real-world examples,
						applying prompt engineering strategies to solve problems across
						different domains.</li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>
