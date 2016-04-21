# Examples for Distributed Computing

Here you can find some _very simple_ examples for [HTML](https://en.wikipedia.org/wiki/HTML), [CSS](https://en.wikipedia.org/wiki/Cascading_Style_Sheets), and [JavaScript](https://en.wikipedia.org/wiki/JavaScript).

The [world wide web](https://en.wikipedia.org/wiki/World_Wide_Web) (WWW) is based on three pillars: [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), [HTML](https://en.wikipedia.org/wiki/HTML)/[CSS](https://en.wikipedia.org/wiki/Cascading_Style_Sheets)/[Javascript](https://en.wikipedia.org/wiki/JavaScript), and [URLs](https://en.wikipedia.org/wiki/Uniform_Resource_Locator). Here we want to focus on the second pillar: The most common standards used to build web pages.

HTML , the hyper text markup language, allows us to write documents which can contain structured text (headlines, paragraphs, tables), but also graphics, or videos, and - most importantly - can be linked to other HTML documents. Such links are the very basis for the WWW, they form the "web".

HTML alone is limited in several ways: First, there are not so many ways to create a visual appealing style for a web page. Yes, we can set table borders, images, fonts, bold/italic, etc., but that's pretty much it. Also, we would ideally like to separate the content of a page from the style and/or have multiple styles for a given page, maybe one for displaying on a screen and one for printing. Second, a HTML page is dead, it does perform no activity. But maybe we want some activity? Maybe we want to dynamically load some additional contents, maybe we want to have menus that fade in and out when the moves hovers over them.

We can solve the first shortcoming by using CSS. Stylesheets allow us to specify, for basically each HTML element, how it should be displayed. We can choose from a much richer pool of stylistic elements. And we can separate the style away from the page into a separate file. This way, all of the web pages of our web site can use the same style. If we change the style, it will automatically apply to the whole page. And we can have different styles for printing and displaying on a screen.

The second shortcoming, the "deadness" of the pages, is solved by using JavaScript. JavaScript is a programming language, while HTML is not. Hence, we can specify activities in JavaScript. We can dynamically modify the current page (which also means that we can dynamically add content and load it from different sources) and react to user input. We can execute certain actions and code if the user clicks a specific button or link. Long story short: With JavaScript, we can create the feeling of using a real local application on the client side, in the web browser of our user.

## 1. `example_plain`

Just a plain HTML document with an image inside.

1. [example_pain/index.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_pain/index.html)


## 2. `example_css`

A really minimal example HTML document with a CSS style sheet.

1. [example_css/index.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_css/index.html)

## 3. `example_javascript`

A HTML document which prints the current date and time via JavaScript.

1. [example_javascript/index.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_javascript/index.html)

## 4. `example_javascript_calculator`

A HTML document which can be used as calculator. The user can enter an expression into a text box. When she presses a button, this expression is evaluated as JavaScript expression and the evaluation result is printed. Of course, this is an example for JavaScript. 

1. [example_javascript_calculator/index.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_javascript_calculator/index.html)

## 5. `pse`: The Periodic System of Elements

This is actually quite an old example which I did, I believe, back in high school. It shows the [periodic table of elements](https://en.wikipedia.org/wiki/Periodic_table). The single elements are clickable. When you click them, a small window with detailed information opens.

This example uses HTML, JavaScript, and CSS. It is in German language, though, and probably a bit out-dated in terms of the element data. 

1. [pse/index.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/pse/index.html)
