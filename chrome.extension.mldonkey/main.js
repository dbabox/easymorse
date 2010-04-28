var url=null;

$('a[href^=ed2k]').click(function(){
	
	chrome.extension.sendRequest({getParam:"url"}, function(response){
		url=response.url;
	});
	
	if(url==null || url==""){
		window.alert("mldonkey服务器地址为空。请设置选项中的url为mldonkey服务器地址。");
	}else{
		window.open(url+"/submit?q="+escape(this.href),"mldonkey","width=300,height=200");
	}
});

