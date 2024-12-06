<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="banner">
		<div class="contianer">
			<div class="landing-banner">
				<br> <br>
				<h1 class="banner-heading">Join now get the class certificate</h1>
				<br> <br> <br>
				<p class="banner-content">Choose from over 4,000 courses and
					trainings in topics like software development, cloud
					infrastructure, IT security, DevOps practices, programming
					languages, and more.</p>

				<img class="book-shape"
					src="${pageContext.request.contextPath}/Views/Images/book.png" />
			</div>
		</div>
	</div>

	<div class="cont-learning">
		<div class="similar-courses">
			<div class="course-header">
				<h2 style="width: auto;">Let's Continue Learning</h2>
				<a
					href="<%=pageContext.getServletContext().getContextPath()%>/myLearning"
					class="see-all">See all &gt;&gt;</a>
			</div>
			<div class="courses">
				<!-- Last two enrollments section -->
				<c:if test="${not empty lastTwoEnrollments}">
					<c:forEach var="enrollment" items="${lastTwoEnrollments}">
						<div class="courses-list">
							<a
								href="${pageContext.request.contextPath}/lessonplayer?courseId=${enrollment.courseId}"
								class="course-link">

								<div class="pending">
									<img
										src="${pageContext.request.contextPath}${enrollment.coverImageUrl}"
										alt="${enrollment.courseTitle}" class="course-image">
									<div class="sub_container">
										<br>
										<p class="course-instructor">Courses |
											${enrollment.instructorName}</p>
										<h3 class="course-title">${enrollment.courseTitle}</h3>
										<div class="progress-bar">
											<p>Go to Lessons</p>
										</div>
									</div>
								</div>
							</a>
						</div>
					</c:forEach>
				</c:if>

				<!-- Message if user is not logged in -->
				<c:if test="${empty lastTwoEnrollments}">
					<p>No enrollments found! Please login or enroll into the
						course.</p>
				</c:if>
			</div>
		</div>
	</div>

	<div class="similar-courses">
		<div class="course-header">
			<h2 style="width: auto;">Recently Added</h2>
			<a href="${pageContext.request.contextPath}/allCourses" class="see-all">See all &gt;&gt;</a>
		</div>
		<div class="courses-list">
			<c:forEach var="course" items="${lastFourCourses}">
				<div class="course"
					onclick="window.location.href='courseDetail?courseId=${course.courseId}'">
					<img
						src="${pageContext.request.contextPath}${course.coverImageUrl}"
						alt="${course.courseTitle}" class="course-image" />
					<div class="sub_container">
						<h3 class="course-title">${course.courseTitle}</h3>
						<p class="course-instructor">${course.instructorName}</p>
						<p class="course-price">$ ${course.price}</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
	
	

</body>
</html>
