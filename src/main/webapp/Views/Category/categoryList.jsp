<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.List, net.elearning.model.Category"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Category List</title>

<!-- Bootstrap CSS from CDN -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/sidenav.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Views/Css/util.css" />

<style>
/* Custom styling for table header */
th {
	background-color: #f2f2f2;
	color: #333;
}

th, td {
	text-align: center;
	vertical-align: middle;
}

table {
	border-radius: 8px;
	border: 1px solid #ddd;
}

table td {
	padding: 15px;
}
/* Optional: Make buttons more rounded */
.btn {
	border-radius: 5px;
}

.category-header {
    position: absolute; /* Make it absolute */
    top: 20px; /* Adjust vertical position as needed */
    right: 140px; /* Align to the right edge */
    display: flex;
    align-items: center; /* Vertically center items */
}
</style>
</head>
<body>
	<%@ include file="../Frontend/sideNav.jsp"%>

	<div class="category-header">
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
				<a href="#" class="dropdown-item">Profile</a> <a href="#"
					class="dropdown-item" onclick="logout()">Logout</a>
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



	<div class="container mt-5">
		<h1 class="mb-4">Category List</h1>

		<!-- Button to create a new category -->
		<a href="category?action=create" class="btn btn-primary mb-3">Create
			New Category</a>

		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Category Name</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<Category> categories = (List<Category>) request.getAttribute("categories");
				if (categories != null) {
					for (Category category : categories) {
				%>
				<tr>
					<td><%=category.getCategoryName()%></td>
					<td><a
						href="<%=request.getContextPath()%>/category?action=edit&categoryId=<%=category.getCategoryId()%>"
						class="btn btn-warning btn-sm">Edit</a> <a
						href="javascript:void(0);"
						onclick="deleteCategory(<%=category.getCategoryId()%>)"
						class="btn btn-danger btn-sm">Delete</a></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="2">No categories available.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<script>
    function deleteCategory(categoryId) {
        if (confirm('Are you sure you want to delete this category?')) {
            fetch('<%=request.getContextPath()%>/category', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ categoryId: categoryId }) // Sending categoryId in JSON body
            })
            .then(response => {
                if (response.ok) {
                    alert('Category deleted successfully');
                    window.location.reload();
                } else {
                    return response.text().then(text => { throw new Error(text); });
                }
            })
            .catch(error => {
                console.error('Error deleting category:', error.message);
                alert('Error deleting category: ' + error.message);
            });
        }
    }

    </script>



	<script> function toggleDropdown() {
	document.getElementById('userDropdown').classList.toggle('active'); }

	window.onclick = function(event) { if
	(!event.target.closest('.dropdown')) {
	document.getElementById('userDropdown').classList.remove('active'); } }
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

	<!-- Bootstrap JS and dependencies -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
