<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
          integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
          crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
          integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
          crossorigin="anonymous"></script>
  <sec:csrfMetaTags/>
</head>
<body>
<header>
  <div class="clearfix" style="margin: 10px">
    <div class="float-left">
      <a href="${pageContext.request.contextPath}" style="color: #000000;">
        <h2>Phonify</h2>
      </a>
    </div>
    <div class="float-left">
      <a href="<c:url value="/quickOrder"/>" style="margin-left: 5px" class="btn btn-secondary btn-lg"
         role="button">
        <h5>Make quick order</h5>
      </a>
    </div>
    <div class="float-right">
      <sec:authorize access="isAnonymous()">
        <a href="<c:url value="/login"/>">Login</a><br>
      </sec:authorize>
      <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal.username"/>
        <a href="<c:url value="/admin/orders"/>">Orders</a>
        <c:url value="/logout" var="logoutUrl"/>
        <form:form action="${logoutUrl}" method="POST">
          <input type="submit" value="Logout" />
        </form:form>
      </sec:authorize>

      <br>
      <c:if test="${miniCart != null}">
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-dark">
          My cart: ${miniCart.totalCount} items ${miniCart.subtotalPrice}$
        </a>
      </c:if>
    </div>
  </div>
  <hr>
</header>
<main>
  <jsp:doBody/>
</main>
</body>
</html>
