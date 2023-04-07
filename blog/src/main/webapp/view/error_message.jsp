<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${not empty errorMessage}">
    <div class="container" id="errorMessage" style="display: block;">
      <div class="row">
        <div class="col s6 offset-s3">
          <div class="card">
            <div class="card-content">
              <p class="red-text" style="text-align: center">${errorMessage}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script>
        function hideErrorMessage() {
            var eM = document.getElementById("errorMessage");
            setTimeout(function() {
              eM.style.transition = "opacity 1s";
              eM.style.opacity = "0";
              setTimeout(function() {
                eM.style.display = "none";
              }, 1000);
            }, 3000);
        }
        hideErrorMessage();
    </script>
</c:if>