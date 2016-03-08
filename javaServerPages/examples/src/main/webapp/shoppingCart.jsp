<html>
  <head><title>Shopping Cart</title></head>
  <body>
  <jsp:useBean id="cart" scope="session" class="shopping.Cart" />
  <% String command = request.getParameter("submit");
          if("add".equals(command))    { cart.setAdd(request.getParameter("item"));    }
     else if("remove".equals(command)) { cart.setRemove(request.getParameter("item")); } %>
  
  <p>Shopping Card:</p>
    <ol>
      <% for (String element : cart.getItems()) { %>
        <li><%= element %></li>
      <% } %>
    </ol>
  
    <hr>
    <%@ include file="shoppingCartForm.jsp" %>
  </body>
</html>