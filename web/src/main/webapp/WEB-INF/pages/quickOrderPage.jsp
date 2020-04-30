<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Quick Order Page">

  <h3 style="margin: 10px">Quick Order</h3><br>

  <div class="float-left">
    <a href="<c:url value="/productList"/>" style="margin-left: 5px" class="btn btn-secondary btn-lg"
       role="button">
      <h5>Back to product list</h5>
    </a>
  </div>

  <form:form method="POST" modelAttribute="quickOrderForm">
    <br><br>
    <table class="table table-striped table-bordered table-sm" style="text-align: center">
      <thead>
      <tr>
        <td style="width: 20%">Code</td>
        <td style="width: 13%">Quantity</td>
      </tr>
      </thead>
      <c:forEach var="quickOrderItem" items="${quickOrderForm.quickOrderItems}" varStatus="status">
        <tr>
          <td>
            <form:input path="quickOrderItems[${status.index}].code" type="text" class="form-control" placeholder="Phone code"
                        name="code" value="${quickOrderItem.code}"/><br>
            <c:if test="${containErrors}">
              <form:errors path="quickOrderItems[${status.index}].code" cssStyle="color: red"/>
            </c:if>
          </td>
          <td>
            <form:input path="quickOrderItems[${status.index}].quantity" type="text" class="form-control" placeholder="Quantity"
                        name="quantity" value="${quickOrderItem.quantity}"/><br>
            <c:if test="${containErrors}">
              <form:errors path="quickOrderItems[${status.index}].quantity" cssStyle="color: red"/>
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </table>
    <div class="float-right" style="margin: 10px">
      <button class="btn btn-outline-secondary" type="submit">Add to cart</button>
    </div>
  </form:form>
</tags:page>
