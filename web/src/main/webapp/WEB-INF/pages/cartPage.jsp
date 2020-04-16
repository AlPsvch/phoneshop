<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Cart Page">

  <h3 style="margin: 10px">Cart</h3><br>

  <div class="float-left">
    <a href="${pageContext.request.contextPath}/productList" style="margin-left: 5px" class="btn btn-secondary btn-lg"
       role="button">
      <h5>Back to product list</h5>
    </a>
  </div>

  <c:set var="cartItems" value="${cart.cartItems}"/>
  <c:choose>
    <c:when test="${cartItems == null || cartItems.size() <= 0}">
      <p><em>Your cart is empty</em></p>
    </c:when>
    <c:otherwise>
      <form:form id="deleteForm" method="POST">
        <input type="hidden" name="_method" value="DELETE"/>
      </form:form>
      <form:form method="POST" action="${pageContext.request.contextPath}/cart/update"
                 modelAttribute="cartItemsUpdateForm">
        <input type="hidden" name="_method" value="POST"/>
        <br><br>
        <div class="float-right">
          <a href="${pageContext.request.contextPath}/order" class="btn btn-secondary" style="margin: 10px">Order</a>
        </div>
        <table class="table table-striped table-bordered table-sm" style="text-align: center">
          <thead>
          <tr>
            <td style="width: 20%">Brand</td>
            <td style="width: 13%">Model</td>
            <td style="width: 14%">Color</td>
            <td style="width: 10%">Display size</td>
            <td style="width: 10%">Price</td>
            <td style="width: 10%">Quantity</td>
            <td style="width: 10%">Action</td>
          </tr>
          </thead>
          <c:forEach var="cartItem" items="${cartItems}">
            <c:set var="phone" value="${cartItem.phone}"/>
            <c:set var="phoneId" value="${phone.id}"/>
            <tr>
              <td>${phone.brand}</td>
              <td>${phone.model}</td>
              <td>
                <c:forEach var="color" items="${phone.colors}" varStatus="status">
                  <c:out value="${color.code}"/>
                  <c:if test="${not status.last}">, </c:if>
                </c:forEach>
              </td>
              <td>${phone.displaySizeInches}"</td>
              <td>${phone.price}$</td>
              <td>
                <form:input path="cartItems['${phoneId}']" type="text" class="form-control" placeholder="New quantity"
                            name="quantity" value="${cartItem.quantity}"/><br>
                <c:if test="${containErrors}">
                  <form:errors path="cartItems['${phoneId}']" cssStyle="color: red"/>
                </c:if>
              </td>
              <td>
                <button class="btn btn-secondary" formaction="${pageContext.request.contextPath}/cart/${phoneId}"
                        type="submit" form="deleteForm">Delete
                </button>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div class="float-right" style="margin: 10px">
          <button class="btn btn-outline-secondary" type="submit">Update</button>
          <a href="${pageContext.request.contextPath}/order" class="btn btn-secondary">Order</a>
        </div>
      </form:form>
    </c:otherwise>
  </c:choose>
</tags:page>
