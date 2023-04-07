<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Forgot Password</title>
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
        <a href="#" class="brand-logo center">Forgot Password</a>
      </div>
    </nav>

    <%@ include file="error_message.jsp" %>
    <%@ include file="success_message.jsp" %>

    <div class="container">
      <div class="row">
        <div class="col s6 offset-s3">
          <div class="card">
            <div class="card-content">
              <form method="post" action="/forgot-password" class="row">
                <div class="input-field col s12">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    class="validate"
                    required
                  />
                  <label for="email">Email</label>
                </div>
                <div class="col s12 center-align">
                  <button class="btn waves-effect waves-light" type="submit">
                    Reset Password
                  </button>
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
