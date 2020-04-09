<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:page pageTitle="Product List Page">
  <c:url value="/resources/js/addProdToCart.js" var="addProdToCart"/>
  <script src=${addProdToCart}></script>

  <div class="clearfix" style="margin: 25px 10px 10px;">
    <div class="float-left">Phones</div>
    <div class="float-right">
      <form action="<c:url value="/productList"/> ">
        <input class="form-control form-control-sm ml-3 w-75" type="text" placeholder="Search" name="query">
      </form>
    </div>
  </div>

  <c:choose>
    <c:when test="${productPage.phones == null || productPage.phones.size() <= 0}">
      <p><em>Nothing found</em></p>
    </c:when>
    <c:otherwise>
      <table class="table table-striped table-bordered table-sm" style="text-align: center">
        <thead>
        <tr>
          <td style="width: 20%">Image</td>
          <td style="width: 13%"><tags:sortingHeader headerName="Brand" sortByLabel="brand"/></td>
          <td style="width: 13%"><tags:sortingHeader headerName="Model" sortByLabel="model"/></td>
          <td style="width: 14%">Color</td>
          <td style="width: 10%"><tags:sortingHeader headerName="Display size" sortByLabel="displaySizeInches"/></td>
          <td style="width: 10%"><tags:sortingHeader headerName="Price" sortByLabel="price"/></td>
          <td style="width: 10%">Quantity</td>
          <td style="width: 10%">Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${productPage.phones}">
          <c:set var="phoneId" value="${phone.id}"/>
          <tr>
            <td>
              <a href="${pageContext.request.contextPath}/productDetails/${phoneId}">
                <img style="max-height: 200px;"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
              </a>
            </td>
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
              <input type="text" class="form-control" placeholder="Quantity" name="quantity" id="quantity-${phoneId}"
                     value="1">
              <span id="quantity-message-${phoneId}" style="display: none"></span>
            </td>
            <td>
              <button class="btn btn-outline-dark"
                      onclick="addToCart(${phoneId}, '${pageContext.request.contextPath}/ajaxCart')">Add to cart
              </button>
            </td>
          </tr>
        </c:forEach>
      </table>
      <tags:pagination totalNumber="${productPage.totalNumOfPages}" currentPage="${productPage.currentPage}"/>
    </c:otherwise>
  </c:choose>
</tags:page>
