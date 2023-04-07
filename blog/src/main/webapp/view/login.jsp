<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Login</title>
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
        <a href="#" class="brand-logo center">Login</a>
      </div>
    </nav>
      
    <%@ include file="error_message.jsp" %>

    <div class="container">
      <div class="row">
        <div class="col s12 m8 offset-m2 l6 offset-l3">
          <div class="card">
            <div class="card-content">
              <form method="post" action="/login" class="row">
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
                    id="password"
                    name="password"
                    type="password"
                    class="validate"
                  />
                  <label for="password">Password</label>
                </div>
                <div class="col s12">
                  <label>
                    <input type="checkbox" name="remember-me" />
                    <span>Remember me</span>
                  </label>
                </div>
                <div class="col s12 center-align">
                  <button class="btn waves-effect waves-light" type="submit">
                    Login
                  </button>
                </div>
                <div class="col s12 center-align">
                  <p>Don't have an account? <a href="/register">Register now</a></p>
                  <p><a href="/forgot-password">Forgot your password?</a></p>
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