<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Courses by Category</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="similar-courses">
		<div class="course-header">
			<h2 style="width: auto;">All Courses</h2>
		</div>

		<!-- Display error message if present -->
		<c:if test="${not empty error}">
			<div class="error-message">
				<p style="color: red;">${error}</p>
			</div>
		</c:if>

		<!-- Display the list of courses -->
		<c:if test="${not empty courses}">
			<div class="courses-list">
				<c:forEach var="course" items="${courses}">
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
		</c:if>

		<!-- Message when no courses are found -->
		<c:if test="${empty courses and empty error}">
			<div class="no-courses-message">
				<p>No courses found for this category.</p>
			</div>
		</c:if>
	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>
s