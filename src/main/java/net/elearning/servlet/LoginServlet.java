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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao(); // Initialize UserDao
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Serve the login page
        request.getRequestDispatcher("/Views/User/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/Views/User/login.jsp").forward(request, response);
            return;
        }

        // Authenticate user
        User user = userDao.getUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userType", user.getUserType());
            session.setAttribute("fullName", user.getFullName());

            // Redirect based on user type
            if ("Instructor".equals(user.getUserType())) {
                response.sendRedirect(request.getContextPath() + "/course");
            } else if ("Student".equals(user.getUserType())) {
                response.sendRedirect(request.getContextPath() + "/index");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            // Login failed
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/Views/User/login.jsp").forward(request, response);
        }
    }
}
