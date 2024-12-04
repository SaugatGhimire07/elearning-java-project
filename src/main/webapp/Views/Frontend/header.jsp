<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
		<header>
			<div class="navbar">
				<div class="logo">
					Next<span>Academy</span>
				</div>
				<div class="search-bar">
					<input type="text" placeholder="What do you want to learn?" />
					<button class="search-btn">
						<img
							src="${pageContext.request.contextPath}/Views/Images/Search.svg"
							alt="Search" />
					</button>
				</div>
				<div class="user-info">
					<div class="notification">
						<img
							src="${pageContext.request.contextPath}/Views/Images/Notification.svg"
							alt="Notifications" />
					</div>
					<div class="messages">
						<img
							src="${pageContext.request.contextPath}/Views/Images/Cart.svg"
							alt="Cart" />
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
			<a href="#" class="active">Home</a> <a
				href="<%=pageContext.getServletContext().getContextPath()%>/enrollment">My
				Learning</a> <a href="Explore.jsp">Explore</a> <a href="Development.jsp">Development</a>
			<a href="It.jsp">IT & Software</a> <a href="Devops.jsp">DevOps &
				Cloud Computing</a>
		</div>
	</div>
	
	<hr class="navbar-divider" />