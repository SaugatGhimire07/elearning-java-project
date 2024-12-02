<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, net.elearning.model.Lesson" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lesson List</title>
    
    <!-- Bootstrap CSS from CDN -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

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
        /* Custom width for the Description column */
        td:nth-child(2), th:nth-child(2) {
            width: 30%; /* Adjust the width as needed */
        }
        /* Optional: Make buttons more rounded */
        .btn {
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Lesson List</h1>

        <!-- Button to create new lesson -->
        <a href="lesson?action=create&courseId=<%= request.getParameter("courseId") %>" class="btn btn-primary mb-3">Create New Lesson</a>
        
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Lesson Title</th>
                    <th>Content</th>
                    <th>Video URL</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons");
                    if (lessons != null) {
                        for (Lesson lesson : lessons) { 
                %>
                <tr>
                    <td><%= lesson.getLessonTitle() %></td>
                    <td><%= lesson.getContent() %></td>
                    <td><%= lesson.getVideoUrl() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/lesson?action=edit&courseId=<%= lesson.getCourseId() %>&lessonId=<%= lesson.getLessonId() %>" class="btn btn-warning btn-sm">Edit</a>
                        <a href="javascript:void(0);" onclick="deleteLesson(<%= lesson.getLessonId() %>)" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>
                <% 
                        } 
                    } else { 
                %>
                <tr>
                    <td colspan="4">No lessons available.</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    </div>

    <script>
        function deleteLesson(lessonId) {
            if (confirm('Are you sure you want to delete this lesson?')) {
                fetch('lesson', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ lessonId: lessonId })
                })
                .then(response => {
                    if (response.ok) {
                        alert('Lesson deleted successfully');
                        window.location.reload();
                    } else {
                        return response.text().then(text => { throw new Error(text); });
                    }
                })
                .catch(error => {
                    console.error('Error deleting lesson:', error.message);
                    alert('Error deleting lesson: ' + error.message);
                });
            }
        }
    </script>
    
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
