<html>
<head>
<title>编辑图书</title>
<meta name="layout" content="main"></meta>
<script type="text/javascript">
<%
//初始化javascript的book对象
if(book){//修改功能：根据controller的book对象生成js对象
	out<<"""var book={id:'${book.id}',name:'${book.name}'};\n"""
	out<<"""book.initRelativeBooks=[];\n"""
	for(b in book.relativeBooks){
		out<<"""book.initRelativeBooks.push({id:'${b.id}',name:'${b.name}'});\n"""
	}
}else{//新建功能
	out<<"""var book={};"""
	out<<"""book.initRelativeBooks=[];"""
	out<<"""book.relativeBooks=[];"""
}

//slice函数将initRelativeBooks的元素复制给relativeBooks
//在两个变量中存储相关图书元素，是为了点击重置按钮时能够恢复
out<<"book.relativeBooks=book.initRelativeBooks.slice(0);"
%>

//对话框中可选的相关图书数组变量
var chooseRelativeBooks=[];

//刷新相关图书节点
function refreshRelativeBook(){
	$("#relativeBooks").empty();
	for(var i in book.relativeBooks){
		$("#relativeBooks").append("<div>"+book.relativeBooks[i].name+"<input type='button' value='删除' onclick='deleteRelativeBook("+i+")'/></div>");
	}
}

//根据数组下标删除相关图书
function deleteRelativeBook(index){
	book.relativeBooks.splice(index,1);
	refreshRelativeBook();
}

//点击重置按钮执行此方法
function doReset(){
	book.relativeBooks=book.initRelativeBooks.slice(0);
	refreshRelativeBook();
}

//点击对话框中checkbox框执行此方法
function checkRelativeBook(input){
	var b=chooseRelativeBooks[input.value];
	b.checked=input.checked;
}

//用于下面方便的使用reset函数
jQuery.fn.reset = function(fn) {
	return fn ? this.bind("reset", fn) : this.trigger("reset");
};

</script>
</head>
<body>
	<div class="content" style="margin-left: 20px;">
		<g:form  id="bookForm" url="[controller:'book',action:'save']">
			<div>
				书名：<input id="bookName" type="text" name="name" value="${book?.name}" />
			</div>
			<p />
			<div>
				相关图书：<input id="addRelativeBooksButton" type="button" value="增加" />
			</div>
			<div id="relativeBooks"></div>
			<p />
			<hr width="250px" style="margin-top: 10px; margin-bottom: 10px;" />
			<input type="submit" value="保存" /> <input type="reset" value="重置" />
			<input type="hidden" name="id" value="${book?.id}" />
		 </g:form>
	</div>

<div id="addRelativeBooksDialog" title="添加相关图书">
	<div id="addRelativeBooksContainer"></div>
</div>

<script type="text/javascript">
refreshRelativeBook();

//jQuery不直接支持$("#bookForm").reset这种写法，jQuery.fn.reset...后可以这样写
$("#bookForm").reset(function(){
	doReset();
	return true;
});

//创建对话框
$("#addRelativeBooksDialog").dialog({autoOpen:false,buttons:{
	"确认":function(){
		for(var i in chooseRelativeBooks){
			var b=chooseRelativeBooks[i];
			if(b.checked){
				book.relativeBooks.push(b);
			}
		}
		refreshRelativeBook();
		$(this).dialog("close");
	},
	"取消":function(){
		$(this).dialog("close");
	}
}});

//打开对话框
//基本思路是打开对话框，播放loading gif图，ajax回调后去掉loading图，改为相关图书列表
$("#addRelativeBooksButton").click(function(){
	$("#addRelativeBooksDialog").dialog("open");
	$("#addRelativeBooksContainer").empty();
	$("#addRelativeBooksContainer").append('<img alt="loading ..." src="${resource(dir:'images',file:'loading.gif')}" style="margin-left: 120px;margin-top: 20px;"/>');
	var url="${createLink(controller:'book',action:'getNotRelatedBooks',id:book?.id)}"
	url+="?";
	
	for(var i in book.relativeBooks){
		url+="related_id="+book.relativeBooks[i].id+"&";
	}
	
	$.ajax({
			url:url,
			success:function(data){
				$("#addRelativeBooksContainer").empty();
				chooseRelativeBooks=data;

				for(var i in chooseRelativeBooks){
					$("#addRelativeBooksContainer").append('<div><input type="checkbox" value="'+i+'" onclick="checkRelativeBook(this)">'+chooseRelativeBooks[i].name+'</input></div>');
				}
			}
		});
});

//提交表单时拦截，将选择了的新相关图书加入
$("#bookForm").submit(function(){
	for(var i in book.relativeBooks){
		var b=book.relativeBooks[i];
		$('#bookForm').append('<input type="hidden" name="relativeBooks['+i+'].id" value="'+b.id+'" />');
	}
});
</script>
</body>
</html>