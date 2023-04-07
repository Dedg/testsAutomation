<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"
    />
</head>
<body>
    <nav>
      <div class="nav-wrapper">
        <a href="#" class="brand-logo center">Registration</a>
      </div>
    </nav>
    
    <%@ include file="error_message.jsp" %>

    <div class="container">
      <div class="row">
        <div class="col s12 m8 offset-m2 l6 offset-l3">
          <div class="card">
            <div class="card-content">
              <form method="post" action="/register" class="row">
                <div class="input-field col s12">
                  <input
                    id="username"
                    name="username"
                    type="text"
                    class="validate"
                  />
                  <label for="username">Username</label>
                </div>
                <div class="input-field col s12">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    class="validate"
                  />
                  <label for="email">Email</label>
                </div>
                <div class="input-field col s12">
                  <input
                    id="password"
                    name="password"
                    type="password"
                    class="validate"
                  />
                  <label for="password">Password</label>
                </div>
                <div class="input-field col s12">
                  <input
                    id="confirmPassword"
                    name="confirmPassword"
                    type="password"
                    class="validate"
                  />
                  <label for="confirmPassword">Confirm Password</label>
                </div>
                <div class="col s12 center-align">
                  <button class="btn waves-effect waves-light" type="submit">
                    Register
                  </button>
                </div>
                <div class="col s12 center-align">
                  <p>Already have an account? <a href="/login">Login now</a></p>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"
    ></script>
</body>
</html>
