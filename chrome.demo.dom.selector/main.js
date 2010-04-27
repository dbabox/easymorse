$(document).ready(function(){
	var target=null;
	
	$("*").mouseenter(function(e){
	   if(target!=null){
	   	$(target).css("border", "");
	   	$(target).unbind('dblclick');
	   }
	   target=e.target;
		$(target).css("border", "1px solid gray");
		$(target).dblclick(function(){
			window.alert("选择的是："+$(target).html());
		});
	});
	
	$("*").mouseleave(function(){
		$(this).css("border", "");
		$(this).unbind('dblclick');
	});
});
