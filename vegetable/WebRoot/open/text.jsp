<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
String json="{\"type\":\"FeatureCollection\",\"features\":[{\"properties\":{\"address\":\"XXXXXXX1\",\"status\":\"2\"},\"geometry\":{\"coordinates\":[100.70184,36.440038],\"type\":\"Point\"},\"type\": \"Feature\"}]}";

out.print(json);
%>
</html>