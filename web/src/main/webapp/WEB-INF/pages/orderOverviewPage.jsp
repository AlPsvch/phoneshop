<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Order Overview Page">

  <h3 style="margin: 10px">Thank you for your order</h3><br>
  <h4 style="margin: 10px">Order number: ${order.id}</h4>

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

      <br><br><br><br><br><br>
      <p style="margin: 20px" class="text-justify">First name: ${order.firstName}</p>
      <p style="margin: 20px" class="text-justify">Last name: ${order.lastName}</p>
      <p style="margin: 20px" class="text-justify">Address: ${order.deliveryAddress}</p>
      <p style="margin: 20px" class="text-justify">Phone: ${order.contactPhoneNo}</p>
      <p style="margin: 20px; max-width: 40%; word-wrap:break-word;"
         class="text-justify">${order.additionalInformation}</p>
      <div class="float-left" style="margin: 10px">
        <a href="${pageContext.request.contextPath}/productList" style="margin-left: 5px" class="btn btn-secondary btn-lg"
           role="button">
          <h5>Back to shopping</h5>
        </a>
      </div>
    </c:otherwise>
  </c:choose>
</tags:page>
