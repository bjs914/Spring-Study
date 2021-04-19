<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    

<li><a href="<spring:url value="/"/>">홈으로</a></li>
<li><a href="<spring:url value="/market/products/"/>">제품목록</a></li>
<li><a href="<spring:url value="/market/products/add"/>">신제품 추가</a></li>
<li><a href="<spring:url value="/cart/"/>">장바구니</a></li><p/>
<li><a href="<spring:url value="/market/customers/add"/>">고객추가</a></li>
<li><a href="<spring:url value="/market/customers"/>">고객목록</a></li>
<li><a href="<spring:url value="/market/customers2"/>">단골고객</a></li>