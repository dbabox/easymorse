var url=null;

chrome.extension.sendRequest({getParam:"url"}, function(response){
	url=response.url;
});

$('a[href^=ed2k]').click(function(){
	
	chrome.extension.sendRequest({getParam:"url"}, function(response){
		url=response.url;
	});
	
	if(url==null || url==""){
		window.alert("must be set mldonkey server url from option page.");
	}else{
		window.open(url+"/submit?q="+escape(this.href),"mldonkey","width=300,height=200");
	}
});

$('a[href$=.torrent]').click(function(){
	
	chrome.extension.sendRequest({getParam:"url"}, function(response){
		url=response.url;
	});
	
	if(url==null || url==""){
		window.alert("must be set mldonkey server url from option page.");
	}else{
		window.open(url+"/submit?q="+escape("startbt "+this.href),"mldonkey","width=300,height=200");
	}
	return false;
});



