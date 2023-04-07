<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${not empty successMessage}">
    <div class="container" id="successMessage" style="display: block;">
      <div class="row">
        <div class="col s6 offset-s3">
          <div class="card">
            <div class="card-content">
              <p class="green-text" style="text-align: center">${successMessage}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script>
        function hideSuccessMessage() {
            var sM = document.getElementById("successMessage");
            setTimeout(function() {
              sM.style.transition = "opacity 1s";
              sM.style.opacity = "0";
              setTimeout(function() {
                sM.style.display = "none";
              }, 1000);
            }, 3000);
        }
        hideSuccessMessage();
    </script>
</c:if>