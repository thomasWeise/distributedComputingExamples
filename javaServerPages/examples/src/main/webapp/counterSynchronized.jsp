<html>
  <head>
    <title>Visitor Counter</title>
  </head>
  
  <body>
  <%! private long numVisitors = 0;
      private synchronized final long __inc() { return ++numVisitors; } %>
  
    <p>Hello! You are the <%= __inc()%><sup>th</sup> visitor!</p>
       
    <p>Now we are using a synchronized method.</p>
  </body>
</html>