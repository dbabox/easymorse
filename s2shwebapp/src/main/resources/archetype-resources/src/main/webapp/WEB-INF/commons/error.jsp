<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>错误提示</title>
		<script src="<c:url value="/html/js/prototype/prototype.js"/>" type="text/javascript"></script>
	</head>
	<%
	Throwable ex = null;
	if(exception!=null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception)request.getAttribute("javax.servlet.error.exception");
	%>
	<body>
		<div id="content">
				<div>
					<h3>
						系统运行异常:<br>
						<%
							if(ex!=null)
								out.println(ex.getMessage());
						%>
					</h3>
				</div>
				<div>
					<button onclick="history.back();">回退</button>
				</div>
				<div><a href="#" onclick="$('detail_error_msg').toggle();">点击查看错误详细信息</a></div>
				<div id="detail_error_msg" style="display: none">
					<% if (ex != null) { %>
					<pre>
						<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
					</pre>
					<% } %>
				</div>
			</div>
	</body>
</html>