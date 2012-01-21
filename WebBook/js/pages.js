var Page=function(pageNo){
	var that=$('<div class="page"></div>');
	that.pageNo=pageNo;
	that.loadContent=function(){
		that.load('data/page'+pageNo+'.html');
	};
	
	return that;
};

var Pages=function(items,index){
	var pageScroll=$('<div class="pageScroll"></div>');
	var pages=$('<div class="pages"></div>');
	pages.appendTo(pageScroll);
	
	pages.items=items;
	pages.index=index;
	
	$('#content').empty();
	$(pageScroll).appendTo($('#content'));
	
	$(pageScroll).on('touchstart touchmove touchend',function(e){
		
		console.log('touch:'+e.type);
		
		if(e.type=='touchstart'){
			pages.lastX=e.originalEvent.touches[0].pageX;
			pages.moveX=0;
		}
		
		if(e.type=='touchmove'){
			console.log('pages left->'+$(pages).position().left);
			pages.moveX+=e.originalEvent.touches[0].pageX-pages.lastX;
			$(pages).css({ 
		        '-webkit-transform': 'translate3d('+pages.moveX+'px,0px,0px)', 
		        '-webkit-transition-duration': '0ms', 
		        '-webkit-backface-visibility': 'hidden', 
		        '-webkit-transition-property': '-webkit-transform' 
		    });
			
			pages.lastX=e.originalEvent.touches[0].pageX;
		}
		
		if(e.type=='touchend'){
			
		}
	});
	
	var page=Page(0);
	page.appendTo(pages);
	
	page=Page(1);
	page.appendTo(pages);
	page.loadContent();
	
	page=Page(2);
	page.appendTo(pages);
	page.loadContent();
};

function createPages(){
	Pages('items',3);
	console.log('-->create pages.');
}