<!DOCTYPE html>
<html>
<head>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
		sans-serif;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background-color: #f8f8f8;
	padding: 20px;
}

.register-container {
	width: 100%;
	max-width: 400px;
}

h1 {
	font-size: 2rem;
	font-weight: bold;
	margin-bottom: 2rem;
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

input::placeholder {
	color: #999;
}

/* Toggle switch styles */
.toggle-container {
    display: flex;
    align-items: center;
    margin-bottom: 1.5rem;
}

.toggle-label {
    margin-right: auto;
    margin-bottom: 0;
}

.switch {
    position: relative;
    display: inline-block;
    width: 48px;
    height: 24px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #e0e0e0;
    transition: .4s;
    border-radius: 24px;
}

.slider:before {
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    left: 2px;
    bottom: 2px;
    background-color: white;
    transition: .4s;
    border-radius: 50%;
}

input:checked + .slider {
    background-color: #FF7043;
}

input:checked + .slider:before {
    transform: translateX(24px);
}

.create-account-btn {
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

.create-account-btn:hover {
	background-color: #F16A3F;
}

.signin-text {
	text-align: center;
	color: #666;
	margin-bottom: 1.5rem;
}

.signin-link {
	color: #1a1a1a;
	text-decoration: none;
	font-weight: 500;
}

.signin-link:hover {
	color: #FF7043;
}
</style>
</head>
<body>
	<div class="register-container">
		<h1>Sign up to Next Academy</h1>

		<form action="<%=request.getContextPath()%>/register" method="post">
			<div class="form-group">
				<label for="name">Full Name</label> <input type="text" id="name"
					name="fullName" required>
			</div>

			<div class="form-group">
				<label for="email">Email</label> <input type="email" id="email"
					name="email" required>
			</div>

			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" placeholder="6+ characters" required>
			</div>

			<div class="toggle-container">
				<label class="toggle-label">Sign up as Instructor</label> <label
					class="switch"> <input type="checkbox" name="isInstructor">
					<span class="slider"></span>
				</label>
			</div>

			<button type="submit" class="create-account-btn">Create
				Account</button>

			<p class="signin-text">
				Already have an account? <a
					href="<%=request.getContextPath()%>/login" class="signin-link">Sign
					In</a>
			</p>
		</form>
	</div>
</body>
</html>
