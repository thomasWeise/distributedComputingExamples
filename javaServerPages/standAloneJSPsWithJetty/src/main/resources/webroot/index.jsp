<html>
<head>
  <title>JSP with Embedded Jetty</title>
  <link href="static/main.css" media="all" rel="stylesheet" type="text/css" />
</head>
<body>
  <h1>JSP with Embedded Jetty</h1>  
  <p>
  This is an example for generating a <em>fat jar</em> with Maven which includes
  a Java Server Page (JSP) as well as Jetty and all necessary components to
  display it.</p>
  <p>
  Java test: <%
     for(int i = 1; i <= 20; i++) {
       switch(i % 6) {
         case 0: { %><code><%= i%></code><%
                  break; }
         case 1: { %><em><%= i%></em><%
                  break; }
         case 2: { %><b><%= i%></b><%
                  break; }
         case 3: { %><sup><%= i%></sup><%
                  break; }
         case 4: { %><sub><%= i%></sub><%
                  break; }
         default : { %><i><%= i%></i><%
                  break; }
        }
      } %>
  </p>
  <p>
  Check out the <a href="/servlet">servlet</a>!
  </p>
</body>
</html>