<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Course Detail</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/courseDetail.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
</head>
<body>

	<%@ include file="header.jsp"%>
	<div class="course-container">
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
					<c:if test="${isEnrolled}">
						<!-- If the user is enrolled, show the link to the lesson player -->
						<button type="button" class="Enroll"
							onclick="window.location.href='${pageContext.request.contextPath}/lessonplayer?courseId=${course.courseId}'">
							Go to Lessons</button>
					</c:if>
					<c:if test="${!isEnrolled}">
						<!-- If the user is not enrolled, show the Enroll Now button -->
						<button type="submit" class="Enroll">Enroll Now</button>
					</c:if>
				</form>

				<img class="shape"
					src="${pageContext.request.contextPath}/Views/Images/Shape.png" />
			</div>
			<div class="lower-banner">
				<div class="lower-banner-item">
					<h3>${lessonCount}Lessons</h3>
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
					<div class="learn-column">
						<p>&#10003 ${course.learningOutcome}</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>
