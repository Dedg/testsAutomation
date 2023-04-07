<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>My Profile</title>
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
    <%@ include file="header.jsp" %>
    <div class="container">
      <form id="post-form" method="post" action="/profile" class="col s12">
      <div class="row">
        <form class="col s12">
          <div class="card">
            <div class="card-content">
              <div class="card-header">
                    <h5 class="card-header">
                        Your profile
                    </h5>
              </div>
              <div class="row">
                <div class="input-field col s12">
                  <input
                    id="username"
                    name="username"
                    type="text"
                    class="validate"
                    value="<c:out value="${user.getUsername()}"/>"
                    readonly
                  />
                  <label for="username">Username</label>
                </div>
                <div class="input-field col s12 m6">
                  <input
                    id="firstName"
                    name="firstName"
                    type="text"
                    class="validate"
                    value="<c:out value="${user.getFirstName()}"/>"
                  />
                  <label for="firstName">First Name</label>
                </div>
                <div class="input-field col s12 m6">
                  <input
                    id="lastName"
                    name="lastName"
                    type="text"
                    class="validate"
                    value="<c:out value="${user.getLastName()}"/>"
                  />
                  <label for="lastName">Last Name</label>
                </div>
                <div class="input-field col s12">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    class="validate"
                    value="<c:out value="${user.getEmail()}"/>"
                  />
                  <label for="email">Email</label>
                </div>
                <div class="input-field col s12 m6">
                    <input id="age" name="age" type="number" class="validate" value="<c:if test="${not empty user.getAge()}"><c:out value="${user.getAge()}"/></c:if>"/>
                    <label for="age">Age</label>
                </div>
                <div class="input-field col s12 m6">
                  <p>
                    <label>
                      <input name="gender" type="radio" value="male" <c:if test="${user.getGender() eq 'male'}">checked</c:if> />
                      <span>Male</span>
                    </label>
                  </p>
                  <p>
                    <label>
                      <input name="gender" type="radio" value="female" <c:if test="${user.getGender() eq 'female'}">checked</c:if> />
                      <span>Female</span>
                    </label>
                  </p>
                </div>
                <div class="input-field col s12">
                  <textarea id="address" name="address" class="materialize-textarea"><c:out value="${user.getAddress()}"/></textarea>
                  <label for="address">Address</label>
                </div>
                <div class="input-field col s12">
                  <input
                    id="website"
                    name="website"
                    type="url"
                    class="validate"
                    value="<c:out value="${user.getWebsite()}"/>"
                  />
                  <label for="website">Website</label>
                </div>
                <div class="input-field col s12">
                    <button id="save" class="btn waves-effect waves-light" type="submit" name="action">Save</button>
                    <a href="/dashboard" class="btn waves-effect waves-light">Back</a>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
      </form>
    </div>
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"
    ></script>
  </body>
</html>
