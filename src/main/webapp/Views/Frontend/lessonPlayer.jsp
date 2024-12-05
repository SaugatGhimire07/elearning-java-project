<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Lesson Player</title>
<meta name="description" content="Lesson Player" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/lessonPlayer.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="video-container">
		<div class="video-section">
			<!-- Display the selected lesson's video and details dynamically -->
			<iframe width="1006px" height="619.27px"
				src="https://www.youtube.com/embed/${selectedLesson.videoUrl}"
				frameborder="0"
				allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
				allowfullscreen></iframe>
			<div class="description">
				<h2>${selectedLesson.lessonTitle}</h2>
				<h3>Description</h3>
				<p>${selectedLesson.content}</p>
			</div>
		</div>
		<div class="course-content">
			<h3>Course Content</h3>
			<ul>
				<!-- Iterate through the list of lessons dynamically -->
				<c:forEach var="lesson" items="${lessons}">
					<li
						class="${selectedLesson.lessonId == lesson.lessonId ? 'active' : ''}">
						<a
						href="${pageContext.request.contextPath}/lessonplayer?courseId=${courseId}&lessonId=${lesson.lessonId}">
							${lesson.lessonTitle} </a>
					</li>
				</c:forEach>

			</ul>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>
