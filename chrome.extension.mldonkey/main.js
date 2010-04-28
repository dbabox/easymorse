var url=null;

chrome.extension.sendRequest({getParam:"url"}, function(response){
	url=response.url;
});

$('a[href^=ed2k]').click(function(){
	window.alert("使用mldonkey下载，url: "+this.href);
	window.open(url+"/submit?q="+escape(this.href),"mldonkey","width=300,height=200");
});

