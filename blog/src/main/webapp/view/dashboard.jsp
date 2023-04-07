<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
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
    <%@ include file="success_message.jsp" %>
    <%@ include file="error_message.jsp" %>

    <c:if test="${posts.size() > 0}">
         <div class="card-container">
            <c:forEach var="post" items="${posts}">
        <div class="row">
                <div class="col s6 offset-s3">
                    <div class="card" >
                        <div class="card-content">
                            <div class="card-header">
                                <h5 class="card-title">
                                    <c:out value="${post.getTitle()}"/>
                                </h5>
                            </div>

                            <div class="card-title">
                                <h3 class="card-title">
                                    <c:out value="${post.getDescription()}"/>
                                </h3>
                            </div>
                            <div class="card-body">
                                <p class="card-text"><c:out value="${post.getBody()}"/></p>
                            </div>
                            
                        </div>
                        <div class="card-action">
                            <span class="left-align">
                                <a class="btn waves-effect waves-light" href="/posts/${post.getId()}">Read more</a>
                            </span>
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
            </c:forEach>
         </div>
    </c:if>
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <c:if test="${posts.size() == 0}">
          <h1><p style="text-align: center; margin: 0 auto;">No posts were created yet</p></h1>
      </c:if>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>
