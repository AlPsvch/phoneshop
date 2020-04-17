<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Order Page">

  <h3 style="margin: 10px">Order</h3><br>

  <div class="float-left">
    <a href="${pageContext.request.contextPath}/cart" style="margin-left: 5px" class="btn btn-secondary btn-lg"
       role="button">
      <h5>Back to cart</h5>
    </a>
  </div>

  <c:if test="${outOfStock}">
    <p><em>Some items from order went out of stock and were removed from your cart</em></p>
  </c:if><br>
  <c:set var="orderItems" value="${order.orderItems}"/>
  <c:choose>
    <c:when test="${orderItems == null || orderItems.size() <= 0}">
      <p><em>No items in order</em></p>
    </c:when>
    <c:otherwise>
      <table class="table table-striped table-bordered table-sm" style="text-align: left">
        <thead>
        <tr>
          <td style="width: 20%">Brand</td>
          <td style="width: 13%">Model</td>
          <td style="width: 14%">Color</td>
          <td style="width: 10%">Display size</td>
          <td style="width: 10%">Quantity</td>
          <td style="width: 10%">Price</td>
        </tr>
        </thead>
        <c:forEach var="orderItem" items="${orderItems}">
          <c:set var="phone" value="${orderItem.phone}"/>
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
            <td>${orderItem.quantity}</td>
            <td>${phone.price}$</td>
          </tr>
        </c:forEach>
      </table>
      <div class="float-right">
        <table class="table table-striped table-bordered table-sm" style="text-align: left">
          <col width="195">
          <col width="200">
          <tr>
            <th scope="row">Subtotal</th>
            <td>${order.subtotal}$</td>
          </tr>
          <tr>
            <th scope="row">Delivery</th>
            <td>${order.deliveryPrice}$</td>
          </tr>
          <tr>
            <th scope="row">TOTAL</th>
            <td>${order.totalPrice}$</td>
          </tr>
        </table>
      </div>

      <form:form method="POST" action="${pageContext.request.contextPath}/order" modelAttribute="orderForm"
                 cssStyle="margin: 15px">
        <br><br><br><br><br>
        <div class="form-group">
          <label for="firstName">First Name*</label>
          <form:input path="firstName" type="text" class="form-control" cssStyle="width: 400px" id="firstName"
                      placeholder="First Name"/>
          <form:errors path="firstName" cssClass="error" cssStyle="color: red"/>
        </div>
        <div class="form-group">
          <label for="lastName">Last Name*</label>
          <form:input path="lastName" type="text" class="form-control" cssStyle="width: 400px" id="lastName"
                      placeholder="Last Name"/>
          <form:errors path="lastName" cssClass="error" cssStyle="color: red"/>
        </div>
        <div class="form-group">
          <label for="address">Address*</label>
          <form:input path="address" type="text" class="form-control" cssStyle="width: 400px" id="address"
                      placeholder="Address"/>
          <form:errors path="address" cssClass="error" cssStyle="color: red"/>
        </div>
        <div class="form-group">
          <label for="phone">Phone*</label>
          <form:input path="phone" type="text" class="form-control" cssStyle="width: 400px" id="phone"
                      placeholder="Phone"/>
          <form:errors path="phone" cssClass="error" cssStyle="color: red"/>
        </div>
        <div class="form-group">
          <label for="additional">Example textarea</label>
          <form:textarea path="additionalInfo" class="form-control" cssStyle="width: 400px" id="additional"
                         rows="3"></form:textarea>
        </div>
        <button class="btn btn-secondary" type="submit">Order</button>
      </form:form>

    </c:otherwise>
  </c:choose>
</tags:page>
