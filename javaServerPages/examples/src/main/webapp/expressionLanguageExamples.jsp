<% response.setContentType("text/html"); %>
<!DOCTYPE html>
<html>
<head>
  <title>Examples for using the Expression Language (EL) with JSPs</title>
  <style type="text/css">
      h1 { text-align:center }
      ul { max-width:45em;margin-left:auto;margin-right:auto }
      li { margin-top:0.75em; margin-bottom:0.75em;max-width:45em }
      code {word-break:keep-all; white-space:nowrap; color:blue }
      span { color:red }
    </style>
</head>
<body>
<h1>Examples for using the Expression Language (EL)</h1>

<ul>
<li>20 modulo 7 is written as <code>&#x24;{20 mod 7}</code> and evaluates to
    <span>${20 mod 7}</span>.</li>

<li>Is 20*10 different from 200? This question can be written as
    <code>&#x24;{((20*10) ne 200) ? "yes" : "no"}</code>.
    The answer is "<span>${((20*10) ne 200) ? "yes" : "no"}</span>".</li>
    
<li>The context path below which you can find the server pages of this example is
    accessed via <code>&#x24;{pageContext.request.contextPath}</code>, which
    evaluates to "<span>${pageContext.request.contextPath}</span>".</li>

<li>You have accessed this page from address "<span>${pageContext.request.remoteAddr}</span>",
    which I can obtain via <code>&#x24;{pageContext.request.remoteAddr}</code>.</li>
    
<li>The computer and port where this server is listening is "<span>${header.host}</span>",
    accessible via <code>&#x24;{header.host}</code>.</li>
</ul>

</body>
</html>