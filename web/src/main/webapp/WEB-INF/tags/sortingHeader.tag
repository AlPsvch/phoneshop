<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="headerName" type="java.lang.String" required="true" %>
<%@ attribute name="orderByLabel" type="java.lang.String" required="true" %>


<a class="text-dark" style="text-decoration: none"
   href="${pageContext.request.contextPath}/productList?<c:if test="${not empty query}">query=${query}&</c:if>order=${orderByLabel}&orderDir=${(order eq orderByLabel && orderDir eq "ASC") ? "DESC" : "ASC"}&page=${currentPage}">
  ${headerName} &#9650;&#9660;
</a>
