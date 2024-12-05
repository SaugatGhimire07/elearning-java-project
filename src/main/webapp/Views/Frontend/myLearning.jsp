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
	href="${pageContext.request.contextPath}/Views/Css/myLearning.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
<head>
<meta charset="UTF-8">
<title>My Learning</title>
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="cont-learning">
		<div class="similar-courses">
			<div class="course-header">
				<h2 style="width: auto;">My Learning</h2>
			</div>
			<div class="courses">
				<c:if test="${not empty allEnrollments}">
					<c:forEach var="enrollment" items="${allEnrollments}">
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
				<c:if test="${empty allEnrollments}">
					<p>Please log in to see your last enrollments.</p>
				</c:if>
			</div>

		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>