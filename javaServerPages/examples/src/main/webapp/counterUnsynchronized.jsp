<html>
  <head>
    <title>Visitor Counter</title>
  </head>
  
  <body>
  <%! long numVisitors = 0; %>
  
    <p>Hello! You are the <%= ++numVisitors%><sup>th</sup> visitor!</p>
    
    <p>
      <% for(int i = 0; i<= 10; i++) { %>    
        <p>Modifying a member variable in an unsynchronized fashion is dangerous.</p> 
      <% } %>
    </p>
  </body>
</html>