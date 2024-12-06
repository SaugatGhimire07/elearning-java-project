<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Explore Courses</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/explore.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="cont-learning">
		<div class="similar-courses">
			<div class="course-header">
				<h2 style="width: auto;">Explore Courses</h2>
			</div>
			<div class="courses">
                <!-- Iterate through the categories and display each one -->
                <c:forEach var="category" items="${categories}">
                    <div class="course-item">
                        <a href="${pageContext.request.contextPath}/courseByCategory?categoryId=${category.categoryId}" class="category-link">
                            <h3>${category.categoryName}</h3>
                        </a>
                    </div>
                </c:forEach>
            </div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>
