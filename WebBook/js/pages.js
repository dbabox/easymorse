var Page = function(pageIndex) {
	var that = $('<div class="page"></div>');
	 that.loadContent = function() {
	 that.load('data/page' + (pageIndex) + '.html');
	 return that;
	 };

	that.index = pageIndex;

	// that.registePageLoadAction=function(){
	// that.parent().on('pageLoad',function(e,pageIndex){
	// console.log('>>page index:'+page.index);
	// });
	// };

	return that;
};

var Pages = function(size, index) {
	var pageScroll = $('<div class="pageScroll"></div>');

	var pages = $('<div class="pages"></div>');
	pages.size = 4;
	pages.index = 0;
	pages.width = 1024;
	pages.css('width', pages.width * pages.size + 'px');

	pages.appendTo(pageScroll);

	pages.isRevert = function() {
		return Math.abs(pages.moveX) < 1024 / 3;
	};

	$('#content').empty();
	$(pageScroll).appendTo($('#content'));

	$(pageScroll).on(
			'touchstart touchmove touchend',
			function(e) {
				e.preventDefault();

				if (e.type == 'touchstart') {
					pages.moveX = 0;
					pages.velocity = 0;
					pages.lastX = e.originalEvent.touches[0].pageX;
				}

				if (e.type == 'touchmove') {
					pages.velocity = e.originalEvent.touches[0].pageX
							- pages.lastX;
					pages.moveX += e.originalEvent.touches[0].pageX
							- pages.lastX;
					$(pages).css(
							'-webkit-transform',
							'translate3d('
									+ (pages.moveX + pages.width
											* (-pages.index)) + 'px,0,0)');

					pages.lastX = e.originalEvent.touches[0].pageX;
				}

				var moveX = 0;

				if (e.type == 'touchend') {
					console.log('velocity:'+pages.velocity);
					if (!pages.isRevert() || Math.abs(pages.velocity)>2) {
						console.log('pages index:' + pages.index);
						if (pages.moveX < 0) {
							if (pages.index <= pages.size - 2) {
								pages.index++;
							}
						} else {
							if (pages.index > 0) {
								pages.index--;
							}
						}
					}

					moveX = pages.width * (-pages.index);

					$(pages).css(
							{
								'-webkit-transform' : 'translate3d(' + moveX
										+ 'px,0,0)',
								'-webkit-transition-duration' : '0.5s'
							});
				}
			});

	$(pages).on('webkitTransitionEnd', function(e) {
		$(pages).css({
			'-webkit-transition-duration' : ''
		});

	});

	for ( var i = 0; i < pages.size; i++) {
		 Page(i+1).loadContent().appendTo(pages);
		//Page(i).appendTo(pages).registePageLoadAction();
	}
};

function createPages() {
	Pages(4, 0);
	console.log('-->create pages.');
}