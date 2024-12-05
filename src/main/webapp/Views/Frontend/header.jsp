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
					<img src="${pageContext.request.contextPath}/Views/Images/Cart.svg"
						alt="Cart" />
				</div>
				<div class="dropdown" id="userDropdown">
					<button class="profile-btn" onclick="toggleDropdown()">
						<%=session.getAttribute("fullName") != null
		? session.getAttribute("fullName").toString().substring(0, 1).toUpperCase()
		: "G"%>
					</button>

					<div class="dropdown-content">
						<%
						if (session.getAttribute("userId") != null) {
						%>
						<a href="#" class="dropdown-item">Profile</a>
						<a href="#" class="dropdown-item" onclick="logout()">Logout</a>
						<%
						} else {
						%>
						<!-- User is not logged in -->
						<a
							href="<%=pageContext.getServletContext().getContextPath()%>/login"
							class="dropdown-item">Login</a> <a
							href="<%=pageContext.getServletContext().getContextPath()%>/register"
							class="dropdown-item">Sign Up</a>
						<%
						}
						%>
					</div>
				</div>

			</div>
		</div>
	</header>
	<div class="navbar1">
		<a href="<%=pageContext.getServletContext().getContextPath()%>/index">Home</a>
		<a
			href="<%=pageContext.getServletContext().getContextPath()%>/myLearning">My
			Learning</a> <a href="<%=pageContext.getServletContext().getContextPath()%>/explore">Explore</a> 
	</div>
</div>

<hr class="navbar-divider" />

<script>
	function toggleDropdown() {
		document.getElementById('userDropdown').classList.toggle('active');
	}

	window.onclick = function(event) {
		if (!event.target.closest('.dropdown')) {
			document.getElementById('userDropdown').classList.remove('active');
		}
	}
</script>

<script>
    function logout() {
        // Create a form to send a POST request to the LogoutServlet
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%=pageContext.getServletContext().getContextPath()%>/logout';

        // Submit the form
        document.body.appendChild(form);
        form.submit();
    }
</script>