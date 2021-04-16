<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script
src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js">
</script>
<!-- <script src="/webstore/resources/js/controller.js"></script> 이렇게 해도됨-->
<script src="<%=request.getContextPath() %>/resources/js/controller.js"></script>
<!-- /webstore 자리에 :  request.getContextPath()를 넣어도 됨  스크립트릿 써서-->
<title>쇼핑카트</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>쇼핑 카트</h1>
				<p>All the selected products in your cart</p>
			</div>
		</div>
		</section>
		<section class="container" ng-app="cartApp">
			<div ng-controller="cartCtrl" ng-init="initCartId('${cartId}')">
				<div>
					<a class="btn btn-danger pull-left" ng-click="clearCart()"> <span
						class="glyphicon glyphicon-remove-sign"></span> Clear Cart
					</a> <a href="<spring:url value='/checkout?cartId=${cartId}'/>" class="btn btn-success pull-right"> <span
						class="glyphicon-shopping-cart glyphicon"></span> Check out
					</a>
				</div>
				<table class="table table-hover">
					<tr>
						<th>Product</th>
						<th>Unit price</th>
						<th>Qauntity</th>
						<th>Price</th>
						<th>Action</th>
					</tr>
					<tr ng-repeat="item in cart.cartItems">
					<!-- ng-repeat : AngularJS활용법중 하나, for문과 같이 배열의 원소들을 반복적으로 출력할때 사용 -->
					<!-- item in cart.cartItems : controller.js(AngularJS)에서 정의 되어있음 -->
						<td>{{item.product.productId}}-{{item.product.name}}</td>
						<td>{{item.product.unitPrice}}</td>
						<td>{{item.quantity}}</td>
						<td>{{item.totalPrice}}</td>
						<td><a href="#" class="label label-danger"
							ng-click="removeFromCart(item.product.productId)"> <span
								class="glyphicon glyphicon-remove" /></span> Remove
						</a></td>
					</tr>
					<tr>
						<th></th>
						<th></th>
						<th>Grand Total</th>
						<th>{{cart.grandTotal}}</th>
						<th></th>
					</tr>
				</table>
				<a href="<spring:url value="/market/products" />"
					class="btn btn-default"> <span
					class="glyphicon-hand-left glyphicon"></span> Continue shopping
				</a>
			</div>
		</section>
</body>
</html>