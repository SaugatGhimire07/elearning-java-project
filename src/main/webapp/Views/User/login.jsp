<!DOCTYPE html>
<html>
<head>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
    }

    body {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background-color: #f8f8f8;
    }

    .login-container {
      width: 100%;
      max-width: 400px;
      padding: 20px;
    }

    h1 {
      font-size: 2rem;
      font-weight: bold;
      margin-bottom: 2rem;
      text-align: center;
    }

    .form-group {
      margin-bottom: 1.5rem;
    }

    label {
      display: block;
      margin-bottom: 0.5rem;
      font-weight: 500;
      color: #1a1a1a;
    }

    input {
      width: 100%;
      padding: 12px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 1rem;
    }

    input:focus {
      outline: none;
      border-color: #FD856C;
    }

    .sign-in-btn {
      width: 100%;
      padding: 14px;
      background-color: #FF7043;
      color: white;
      border: none;
      border-radius: 999px;
      font-size: 1rem;
      cursor: pointer;
      margin-bottom: 1.5rem;
      transition: background-color 0.2s;
    }

    .sign-in-btn:hover {
      background-color: #F16A3F;
    }

    .signup-text {
      text-align: center;
      color: #666;
    }

    .signup-link {
      color: #1a1a1a;
      text-decoration: none;
      font-weight: 500;
    }

    .signup-link:hover {
      color: #FF7043;
    }
  </style>
</head>
<body>
  <div class="login-container">
    <h1>Sign in to NextAcademy</h1>

    <form action="<%=request.getContextPath() %>/login"  method="post">
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
      </div>

      <button type="submit" class="sign-in-btn">Sign In</button>
    </form>

    <p class="signin-text">
	  Already have an account? <a href="<%=request.getContextPath() %>/register" class="signup-link">Sign Up</a>
	</p>
  </div>
</body>
</html>
