

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Menu</title>
</head>
<body>
    <s:text name="Menu DropMusic:"/>

    <p><a href="<s:url action="logout" />">Log Out</a></p>
    <p><a href="<s:url action="makeEditor"/>">Make Editor</a></p>

</body>
</html>