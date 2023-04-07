<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Blog Details</title>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"
    />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  </head>
  <body>
    <%@ include file="header.jsp" %>
    <div class="container">
      <div class="row">
        <div class="col s12">
          <div class="card">
            <div class="card-content">
            <div class="card-header">
                <h5 class="card-header">
                    Blog Details
                </h5>
            </div>
            <span class="card-title"><b><c:out value="${post.getTitle()}"/></b></span>
            <h6><p><c:out value="${post.getDescription()}"/></p></h6>
            <p><c:out value="${post.getBody()}"/></p>
            </div>
            <div class="card-action">
              <div class="row">
                <div class="col s12 m6">
                  <p>Author: <c:choose>
                            <c:when test="${not empty post.getAuthor().getFirstName()}"><c:out value="${post.getAuthor().getFirstName()}"/> <c:out value="${post.getAuthor().getLastName()}"/></c:when>
                            <c:otherwise><c:out value="${post.getAuthor().getUsername()}"/></c:otherwise>
                            </c:choose></p>
                  <p>Posted on: <c:out value="${post.getCreatedAt()}"/></p>
                </div>
                <div class="col s12 m6">
                <c:if test="${post.getAuthor().getId() == currentUser.getId()}">
                    <span style="float: right;">
                        <a class="btn waves-effect waves-light" href="/posts/${post.getId()}/edit">Edit</a>
                    </span>
                    <span style="float: right;">
                        <a class="btn waves-effect waves-light red" href="/posts/${post.getId()}/delete">Delete</a>
                    </span>
                </c:if>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col s12">
          <h5>Comments</h5>
          <ul class="collection">
            <c:forEach var="comment" items="${post.getComments()}">
                <li class="collection-item">
                    <c:if test="${comment.getAuthor().getId() == currentUser.getId()}">
                        <a href="/posts/${post.getId()}/comments/${comment.getId()}/delete" class="secondary-content"><i class="material-icons">delete</i></a>
                    </c:if>
                    <span class="title"><c:choose>
                              <c:when test="${not empty comment.getAuthor().getFirstName()}"><c:out value="${comment.getAuthor().getFirstName()}"/> <c:out value="${comment.getAuthor().getLastName()}"/></c:when>
                              <c:otherwise><c:out value="${comment.getAuthor().getUsername()}"/></c:otherwise>
                              </c:choose></span>
                    <p><c:out value="${comment.getMessage()}"/></p>
                </li>
                
            </c:forEach>
          </ul>
          <c:if test="${not empty currentUser}">
            <form class="col s12" method="post" action="/posts/${post.getId()}/comment">
              <div class="row">
                <div class="input-field col s12">
                  <input id="comment" name="comment" type="text" required></input>
                  <label for="comment">Comment</label>
                </div>
                <div class="col s12">
                  <button class="btn waves-effect waves-light" type="submit" name="action">Add Comment</button>
                </div>
              </div>
            </form>
          </c:if>
        </div>
      </div>
    </div>
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"
    ></script>
  </body>
</html>
