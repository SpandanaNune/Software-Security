<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript">
    /**  How to setup a textarea that allows typing with a Hindi Virtual Keyboard. */
    
    google.load("elements", "1", {packages: "keyboard"});
    
    function onLoad() {
      var content = document.getElementById('content');
      // Create the HTML for out text area
      content.innerHTML = '<textarea id="textCode" name="textCode" cols="100" rows="5"></textarea>';
    
      var kbd = new google.elements.keyboard.Keyboard(
          [google.elements.keyboard.LayoutCode.ENGLISH],
          ['textCode']);
    }
    
    google.setOnLoadCallback(onLoad);
    
    </script>
</head>
<body>
<h1>How to build virtual keyboard for your web application</h1>
   <form method="post">
    <div id="content">Loading...</div>
    <input type="submit" value="Submit" />
   </form>
</body>
</html>