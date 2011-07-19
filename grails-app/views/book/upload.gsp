<html>
<head>
<title>上传图书资源</title>
<meta name="layout" content="main"></meta>
<script type="text/javascript">

</script>
</head>
<body>
<div style="margin-left: 15px;">
<g:message code="${flash.message}" />
<g:form action="uploadFile" method="post" enctype="multipart/form-data">
<input type="file" name="myFile" />
<input type="submit" value="上传" />
<input type="hidden" name="id" value="1"/>
</g:form>
</div>
</body>
</html>