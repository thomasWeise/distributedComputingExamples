<html>
  <head>
    <title>Rate this Courese</title>
  </head>

<%! int   ratingsCount = 0;
    int[] ratings  = new int[6]; %>
  
  <body>
    <h1>Course Evaluation</h1>
    
    <h2>Rate this Course</h2>
    <form method="get">    
      <p>Give a rating how you like this course:</p>
      <table border="1">
        <tr>
          <td bgcolor="#ff0000">rubbish, makes no sense<input type="radio" name="rating" value="1" /></td>          
          <td bgcolor="#ff8888">pretty boring          <input type="radio" name="rating" value="2" /></td>
          <td bgcolor="#ffff00">so-so                  <input type="radio" name="rating" value="3" /></td>
          <td bgcolor="#88ff88">not bad                <input type="radio" name="rating" value="4" /></td>
          <td bgcolor="#00ff00">OMG, this is great     <input type="radio" name="rating" value="5" /></td>
          <td bgcolor="#888888">Honestly, I don't care <input type="radio" name="rating" value="6" /></td>
        </tr>
      <tr><td colspan="6">
        <input type="submit" value="submit my rating" />
      </td></tr>
      </table>
    </form>
  
    <%  String  rating = request.getParameter("rating"); //get the "GET" parameter, i.e., the form result
        int     ratingInt   = -1;
        if (rating != null) {//if the page is not loaded because of form submit, rating will be null      
          try { //only if submit was clicked, we get here
            ratingInt = Integer.parseInt(rating); //check if rating is int (as it should be)
          } catch (Exception e) {
            ratingInt = -1;
          }
            
          if ((ratingInt >= 1) && (ratingInt <= ratings.length)) { //a valid rating was cast
            synchronized(this) { //synchronized update and read of member variables
            ratings[ratingInt - 1]++;
            ratingsCount++;
    %>
  
    <h2>Ratings</h2>  
    <p>So far <%= ratingsCount%> valid ratings have been issued.</p>
      <table border="1">
        <tr>
          <th bgcolor="#ff0000">rubbish, makes no sense</th>          
          <th bgcolor="#ff8888">pretty boring</th>
          <th bgcolor="#ffff00">so-so</th>
          <th bgcolor="#88ff88">not bad</th>
          <th bgcolor="#00ff00">OMG, this is great</th>
          <th bgcolor="#888888">Honestly, I don't care</th>
        </tr>
        <tr>
        <% for(int i = 0; i < ratings.length; i++) { %>
          <td><%= ratings[i]%></td>
          <% } // for%>
        </tr>
      </table>
    
    <% } } } // if%>
  </body>
</html>