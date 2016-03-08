<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>Examples for using the JSP Standard Tag Library</title>
  <style type="text/css">
      h1 { text-align:center }
      ul { max-width:45em;margin-left:auto;margin-right:auto }
      li { margin-top:0.75em; margin-bottom:0.75em;max-width:45em }
      code {word-break:keep-all; white-space:nowrap; color:blue }
      pre {word-break:keep-all; white-space:nowrap; color:blue }
      span { color:red }
    </style>
</head>
<body>
<h1>Examples for using the JSP Standard Tag Library</h1>
  
<jsp:useBean id="ourDate" class="java.util.Date" />
<jsp:useBean id="format" class="java.text.SimpleDateFormat" />

<ul>
<li>
<code>&lt;c:if test="&#x24;{ourDate.time > 1460217600000}">Wow, it is after 2016-04-10.&lt;/c:if></code>
evaluates to "<span><c:if test="${ourDate.time > 1460217600000}">Wow, it is after 2016-04-10.</c:if></span>"
</li>

<li>
if-then-else style expressions can be written as:
<pre>
&lt;c:choose><br/>
&lt;c:when test="&#x24;{ourDate.time > 1460217600000}">Wow, it is after 2016-04-10.&lt;/c:when><br/>
&lt;c:otherwise>No, it is before 2016-04-10.&lt;/c:otherwise><br/>
&lt;/c:choose></pre>
which evaluates to 
"<span><c:choose>
<c:when test="${ourDate.time > 1460217600000}">Wow, it is after 2016-04-10.</c:when>
<c:otherwise>No, it is before 2016-04-10.</c:otherwise>
</c:choose></span>"
</li>

<li>
Loops over numerical variables can be implemented as follows:
<pre>
Gray scale color values:<br/>
&lt;c:forEach var="col" begin="0" end="255"><br/>
&nbsp;&lt;span style="color:rgb(&#x24;{col},&#x24;{col},&#x24;{col})">&#x24;{col}&lt;/span><br/>
&lt;/c:forEach><br/>
</pre> becomes<br/>
Gray scale color values:
  <c:forEach var="col" begin="0" end="255">
    <span style="color:rgb(${col},${col},${col})">${col}</span>
  </c:forEach>
</li>

<li>
We can also loop over collections, such as the week day names:
<code>&lt;c:forEach var="w" items="&#x24;{format.dateFormatSymbols.weekdays}">&#x24;{w}&lt;/c:forEach></code>
becomes
"<span><c:forEach var="w" items="${format.dateFormatSymbols.weekdays}"> ${w}</c:forEach></span>"
</li>

</ul>

</body>
</html>