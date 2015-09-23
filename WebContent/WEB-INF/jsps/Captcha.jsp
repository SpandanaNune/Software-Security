<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<html>
<head>
<title>Google ReCaptcha with SpringMVC</title>
<style type="text/css">
    body {
        font-family: verdana;
        font-size: 12px;
        margin: 40px;
    }
    h2 {
        background-color: #5284DC;
        color: #fff;
        border-radius:4px;
        width: 360px;
        padding: 5px;
        height: 30px;
    }
</style>
</head>
<body>
 
<h2>Google ReCaptcha with SpringMVC</h2>
    <form action="validate" method="post">
        <%
        ReCaptcha c = ReCaptchaFactory.newReCaptcha(
            "6LeeTusSAAAAAPwiEGXXXXXXXXXXXXXXXXXXXXXX",
            "6LeeTusSAAAAAJ-XXXXXXXXXXXXXXXXXXXXXXXXX",
            false);
        out.print(c.createRecaptchaHtml(null, null));
        %>
        <input type="submit" value="Validate Captcha" />
    </form>
</body>
</html>