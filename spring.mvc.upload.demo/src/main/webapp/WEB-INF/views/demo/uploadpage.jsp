<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>fileupload</title>
</head>
<body>
<center>
<h1>文件上传</h1><hr />
<form action="submit.do" method="post" enctype="multipart/form-data">
<p><label>选择：<input type="file" name="files" /></label></p>
<p><input type="submit" value="上传" /></p>
</form>
</center>
</body>
</html>