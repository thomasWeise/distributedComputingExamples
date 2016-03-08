<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head><title>Shopping Cart</title></head>
  <body>
  
  <jsp:useBean id="cart" scope="session" class="shopping.Cart" />  
  
  <c:if test="${not empty param.submit}">
    <c:set target="${cart}" property="${param.submit}" value="${param.item}" />
  </c:if>
      
  <p>Shopping Card:</p>
    <ol>
      <c:forEach var="element" items="${cart.items}">
        <li>${element}</li> 
      </c:forEach>
    </ol>
  
    <hr>
    <%@ include file="shoppingCartForm.jsp" %>
  </body>
</html>