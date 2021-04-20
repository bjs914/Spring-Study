<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><tiles:insertAttribute name="title" /></title>
<link rel="stylesheet"
href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script
src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js">
</script>
<script src="/webstore/resources/js/controller.js"></script>
</head>
<body>
	<section>
	<div class="pull-right" style="padding-right: 50px">
		<a href="?language=ab">한글</a>|<a href="?language=en">English</a>
		<c:choose>
			<c:when test="${userId == null}">
				<a href="<c:url value='/login'/>"> 
			<spring:message code="login.anchor.text" />
			</a>
			</c:when>
			<c:otherwise>
				환영&nbsp;'${userId}'님&nbsp;
				<a href="<c:url value='/logout'/>"> 
			<spring:message code="logout.anchor.text" /> </a>
			</c:otherwise>
		</c:choose>		
	</div>
</section>
		<div class="container">
			<div class="jumbotron">	<!-- jumbotron에 heading과 tagline이 들어감 -->
				<div class="header">
					<ul class="nav nav-pills pull-right">
						<tiles:insertAttribute name="navigation" />
					</ul>
					<h3 class="text-muted">Web Store</h3>
				</div>
				<h1>
					<tiles:insertAttribute name="heading" />
				</h1>
				<p>
					<tiles:insertAttribute name="tagline" />
				</p>
			</div>
			<div class="row">
				<tiles:insertAttribute name="content" />
			</div>
			<div class="footer">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
</body>
</html>