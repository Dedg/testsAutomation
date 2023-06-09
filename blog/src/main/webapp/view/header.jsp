<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav>
  <ul>
    <li class="${fn:escapeXml(activePage) == 'dashboard' ? 'active' : ''}"><a href="/dashboard">Dashboard</a></li>
    <c:if test="${not empty currentUser}">
        <li class="${fn:escapeXml(activePage) == 'add' ? 'active' : ''}"><a href="/posts/add">Add New Post</a></li>
    </c:if>
    <c:if test="${not empty post and activePage == 'edit'}">
        <li class="${fn:escapeXml(activePage) == 'edit' ? 'active' : ''}"><a href="/edit">Edit Post</a></li>
    </c:if>

    <c:if test="${not empty currentUser}">
        <li class="${fn:escapeXml(activePage) == 'profile' ? 'active' : ''}"><a href="/profile">My Profile</a></li>
        <li class="right"><a href="/logout">Logout</a></li>
    </c:if>
    <c:if test="${empty currentUser}">
        <li class="right"><a href="/login">Login</a></li>
        <li class="right"><a href="/register">Register</a></li>
    </c:if>
  </ul>
</nav>