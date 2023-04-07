<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

    <title>${title}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>
     <%@ include file="header.jsp" %>
    <div class="container">
        <form id="post-form" method="post" action="/posts/add" class="col s12">
            <div class="row">
                <div class="input-field col s12">
                    <input type="text" id="title" name="title" required>
                    <label for="title" class="active">Title</label>
                </div>
                <div class="input-field col s12">
                    <input type="text" id="description" name="description" required>
                    <label for="description" class="active">Description</label>
                </div>
                <div class="input-field col s12">
                    <textarea id="body" name="body" class="materialize-textarea" required></textarea>
                    <label for="body" class="active">Body</label>
                </div>
                <div class="input-field col s12">
                    <button id="add" class="btn waves-effect waves-light" type="submit" name="action">Add Post</button>
                    <a href="/dashboard" class="btn waves-effect waves-light">Back</a>
                </div>
            </div>
        </form>
    </div>

    <c:if test="${not empty post}">
        <script>
            function setValues() {
                document.getElementById("add").textContent = 'Save';
                document.getElementById("add").textContent = 'Save';
                document.getElementById("title").value = '<c:out value="${post.getTitle()}"/>';
                document.getElementById("description").value = '<c:out value="${post.getDescription()}"/>';
                document.getElementById("body").value = '<c:out value="${post.getBody()}"/>';
                document.getElementById("post-form").action = '/posts/<c:out value="${post.getId()}"/>/edit';
            }
            setValues();
        </script>
    </c:if>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>