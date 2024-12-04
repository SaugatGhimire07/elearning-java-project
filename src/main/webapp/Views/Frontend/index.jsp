<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NextAcademy</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/index.css" />

</head>
<body>
	<div class="container">
		<header>
			<div class="navbar">
				<div class="logo">
					Next<span>Academy</span>
				</div>
				<div class="search-bar">
					<input type="text" placeholder="What do you want to learn?" />
					<button class="search-btn">
						<img src="icons/Search.svg" />
					</button>
				</div>
				<div class="user-info">
					<div class="notification">
						<img src="icons/Notification.svg" />
					</div>
					<div class="messages">
						<img src="icons/Cart.svg" />
					</div>
					<div class="user-profile">
						<img src="https://via.placeholder.com/32" alt="User" />
						<div class="user">
							<span> <%
 String fullName = (String) session.getAttribute("fullName");
 if (fullName != null) {
 	out.print(fullName);
 } else {
 	out.print("Guest");
 }
 %>
							</span>
							<p>
								<%
								String userType = (String) session.getAttribute("userType");
								if (userType != null) {
									out.print(userType);
								} else {
									out.print("Visitor");
								}
								%>
							</p>
						</div>
					</div>

				</div>
			</div>
		</header>
		<div class="navbar1">
			<a href="#" class="active">Home</a> <a href="<%=pageContext.getServletContext().getContextPath()%>/enrollment">My
				Learning</a> <a href="Explore.jsp">Explore</a> <a href="Development.jsp">Development</a>
			<a href="It.jsp">IT & Software</a> <a href="Devops.jsp">DevOps &
				Cloud Computing</a>
		</div>
		<section class="main-content">
			<div class="certificate-banner">
				<div class="banner1">
					<h1>Join now get the class certificate</h1>
					<p>Choose from over 4,000 courses and trainings in topics like
						software development, cloud infrastructure, IT security, DevOps
						practices, programming languages, and more.</p>
				</div>
				<div class="banner1">
					<img src="icons/Books.svg" alt="Books" />
				</div>
			</div>
		</section>
		<section class="in-progress-section">
			<div class="section-header">
				<h2>In Progress (2)</h2>
				<a href="#">See all &gt;&gt;</a>
			</div>
			<div class="courses">
				<div class="course-card">
					<img src="https://via.placeholder.com/80" alt="React Course" />
					<div class="course-info">
						<div>
							<p class="course-meta">Course | Saugat Ghimire</p>
							<h3 class="course-title">React Basics: The Complete Bootcamp
							</h3>
						</div>
						<div>
							<div class="card-bottom">
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 10%"
										aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
								</div>
								<div class="progress-info1">
									<span>35%</span>
								</div>
							</div>
							<span class="progress-info">Overall Progress</span>
						</div>
					</div>
				</div>
				<div class="course-card">
					<img src="https://via.placeholder.com/80" alt="React Course" />
					<div class="course-info">
						<div>
							<p class="course-meta">Course | Saugat Ghimire</p>
							<h3 class="course-title">React Basics: The Complete Bootcamp
							</h3>
						</div>
						<div>
							<div class="card-bottom">
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 10%"
										aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
								</div>
								<div class="progress-info1">
									<span>35%</span>
								</div>
							</div>
							<span class="progress-info">Overall Progress</span>
						</div>
					</div>
				</div>
			</div>
		</section>
		<section class="popular-certificates-section">
			<div class="section-header">
				<h2>Most Popular Certificate</h2>
				<a href="#">See all &gt;&gt;</a>
			</div>

			<div class="certificates">
				<c:forEach var="course" items="${lastFourCourses}">
					<div class="certificate-card"
						onclick="window.location.href='courseDetail?courseId=${course.courseId}'">
						<img
							src="${pageContext.request.contextPath}${course.coverImageUrl}"
							alt="${course.courseTitle}" />
						<div class="certificate-info">
							<h3 class="certificate-title">${course.courseTitle}</h3>
							<p class="certificate-author">${course.instructorName}</p>
							<p class="certificate-price">${course.price}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>


	</div>
	<footer class="footer">
		<div class="footer-container">
			<div class="footer-container1">
				<div class="footer-column" id="about">
					<ul>
						<li><a href="#">About Us</a></li>
						<li><a href="#">Courses</a></li>
						<li><a href="#">Blog or Resources</a></li>
						<li><a href="#">Frequently Asked Questions</a></li>
						<li><a href="#">Contact Us</a></li>
						<li><a href="#">Help Center</a></li>
						<li><a href="#">Community Forum</a></li>
						<li><a href="#">Register as a Instructor</a></li>
					</ul>
				</div>
				<div class="footer-column">
					<ul>
						<li>Quick Links</li>
						<li><a href="#">Home</a></li>
						<li><a href="#">Courses</a></li>
						<li><a href="#">New Arrivals</a></li>
						<li><a href="#">Most Popular</a></li>
						<li><a href="#">My Profile</a></li>
						<li><a href="#">Enroll Now</a></li>
						<li><a href="#">Refund Policy</a></li>
					</ul>
				</div>
				<div class="footer-column">
					<ul>
						<li>More Information</li>
						<li><a href="#">How It Works</a></li>
						<li><a href="#">Pricing and Plans</a></li>
						<li><a href="#">Testimonials</a></li>
						<li><a href="#">Financial Aid</a></li>
						<li><a href="#">Instructor Information</a></li>
					</ul>
				</div>
			</div>
			<div class="footer-container2">
				<div class="footer-subscribe">
					<h3>Join our email list</h3>
					<p>Get exclusive offers, new course announcements, and more.
						You may unsubscribe at any time.</p>
					<div class="footer-button">
						<input type="email" placeholder="Email address" />
						<button>Subscribe</button>
					</div>
				</div>
			</div>
		</div>
		<div class="footer-bottom">
			<p>
				© 2024 {Brand}. All rights reserved. | <a href="#">Privacy
					Policy</a> | <a href="#">Terms of Use</a>
			</p>
		</div>
	</footer>
</body>
</html>