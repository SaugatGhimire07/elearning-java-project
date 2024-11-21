package net.elearning.servlet;

import net.elearning.dao.UserDao;
import net.elearning.model.User;
import net.elearning.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao(); // Initialize UserDao
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Serve the registration page
        request.getRequestDispatcher("/Views/User/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String fullName = request.getParameter("fullName");
        String userType = "Student";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate input
        if (fullName == null || userType == null || email == null || password == null ||
                fullName.isEmpty() || userType.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/Views/User/register.jsp").forward(request, response);
            return;
        }

        // Hash the password for security
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create a new User object
        User user = new User();
        user.setFullName(fullName);
        user.setUserType(userType);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        // Insert user into the database
        int userId = userDao.insertUser(user);
        if (userId > 0) {
            // User successfully registered
            user.setUserId(userId);

            // Set user information in the session
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userType", user.getUserType());

            // Redirect based on user type
            if ("Instructor".equals(user.getUserType())) {
                response.sendRedirect(request.getContextPath() + "/Views/Frontend/courseList.jsp");
            } else if ("Student".equals(user.getUserType())) {
                response.sendRedirect(request.getContextPath() + "/Views/Frontend/index.jsp");
            } else {
            	// Default fallback
            	System.out.println("An unexpected error occurred. Please try again.");
            	request.getRequestDispatcher("/Views/User/register.jsp").forward(request, response);

            }
        } else {
            // Registration failed
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/Views/User/register.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
