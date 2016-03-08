<html>
  <head>
    <% java.util.Date clock = new java.util.Date(); %>
    <title>Hello! (<%= clock %>)</title>
  <body>

  <% if (clock.getHours() < 15) { %>
    Good Morning!
  <% } else { %>
    Good Evening!
  <% } %>
  
  It is now <%= clock.getHours() %> o'clock and <%= clock.getMinutes() %> minutes.
  </body>
</html>