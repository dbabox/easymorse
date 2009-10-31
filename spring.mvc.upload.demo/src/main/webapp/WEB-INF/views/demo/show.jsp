<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>所有文件</title>
</head>
<body>
<center>
所有已经上传的文件<br /><br /><br />
<table border="1"><tr><td>文件</td><td>文件名</td></tr>
<c:forEach items="${filelist}" var="files">
<tr><td>文件</td><td>${files }</td></tr>
</c:forEach>
</table>
<hr />
<a href="upload.do" >继续上传</a>
</center>
</body>
</html>