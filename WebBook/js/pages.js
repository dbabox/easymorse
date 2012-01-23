var Page = function(pageNo) {
	var that = $('<div class="page"></div>');
	that.loadContent = function() {
		that.load('data/page' + pageNo + '.html');
		return that;
	};

	return that;
};

var Pages = function(size, index) {
	var pageScroll = $('<div class="pageScroll"></div>');
	
	var pages = $('<div class="pages"></div>');
	pages.size=4;
	pages.index = 0;
	pages.width=1024;
	pages.css('width',pages.width*pages.size+'px');

	pages.appendTo(pageScroll);

	pages.isRevert=function(){
		return Math.abs(pages.moveX)<1024/3;
	};

	$('#content').empty();
	$(pageScroll).appendTo($('#content'));

	$(pageScroll).on(
			'touchstart touchmove touchend',
			function(e) {
				e.preventDefault();

				if (e.type == 'touchstart') {
					pages.moveX=0;
					pages.lastX = e.originalEvent.touches[0].pageX;
					console.log('pages index:'+pages.index);
				}

				if (e.type == 'touchmove') {
					pages.moveX += e.originalEvent.touches[0].pageX
							- pages.lastX;
					$(pages).css('-webkit-transform',
							'translate3d(' + (pages.moveX+pages.width*(-pages.index)) + 'px,0,0)');

					pages.lastX = e.originalEvent.touches[0].pageX;
				}
				
				var moveX=0;

				if (e.type == 'touchend') {
					if(!pages.isRevert()){
						if(pages.moveX<0){
							if(pages.index<pages.size-1){
								pages.index++;
							}
						}else{
							if(pages.index>0){
								pages.index--;
							}
						}
						moveX=pages.width*(-pages.index);
					}else{
					}
					
					$(pages).css({
						'-webkit-transform':'translate3d(' + moveX + 'px,0,0)',
						'-webkit-transition-duration':'0.5s'
					});
				}
			});
	
	$(pages).on('webkitTransitionEnd',function(e){
		$(pages).css({
			'-webkit-transition-duration':''
		});
		
		if(!pages.isRevert() &&pages.moveX>0){//上一页
			
		}
		
		if(!pages.isRevert() &&pages.moveX<0){//下一页
			$(pages).css({
				//'-webkit-transform':'translate3d(0px,0,0)'
			});
			
			//console.log('next page');
		}
	});

	for(var i=0;i<pages.size;i++){
		Page(i+1).loadContent().appendTo(pages);
	}
};

function createPages() {
	Pages(4, 0);
	console.log('-->create pages.');
}