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
</head>
<body>
	<%@ include file="header.jsp" %>
	
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
				<a href="#" class="see-all">See all &gt;&gt;</a>
			</div>
			<div class="courses">
				<div class="courses-list ">
					<div class="pending">
						<img
							src="${pageContext.request.contextPath}/Views/Images/react.png"
							alt="React Basics" class="course-image">
						<div class="sub_container">
							<br>
							<p class="course-instructor">Courses | Saugat Ghimire</p>
							<h3 class="course-title">React Basics: The Complete Bootcamp</h3>
							<div class="progress-bar">
								<div class="progress" style="width: 20%;"></div>
							</div>
							<p>20% Overall Progress</p>
						</div>
					</div>
				</div>
				<div class="courses-list " style="margin-left: 25px;">
					<div class=" pending">
						<img
							src="${pageContext.request.contextPath}/Views/Images/prompt.png"
							alt="React Basics" class="course-image">
						<div class="sub_container">
							<br>
							<p class="course-instructor">Courses | Rupesh Karki</p>
							<h3 class="course-title">Prompt Engineering</h3>
							<div class="progress-bar">
								<div class="progress" style="width: 20%;"></div>
							</div>
							<p>20% Overall Progress</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="similar-courses">
		<div class="course-header">
			<h2 style="width: auto;">Most Popular Certificate</h2>
			<a href="#" class="see-all">See all &gt;&gt;</a>
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
	
	<%@ include file="footer.jsp" %>

</body>
</html>