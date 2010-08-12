//
//  WebViewDemoViewController.m
//  WebViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "WebViewDemoViewController.h"

@implementation WebViewDemoViewController

@synthesize WebView;

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	Nav.topItem.title=@"Welcome";
	Nav.topItem.leftBarButtonItem=nil;	
	
	//NSString *HTMLData = @"Hello this is a test Hello this is a test Hello this is a test Hello this is a test Hello this is a test<p><img src=\"005-1.jpg\" alt=\"picture\"/>";
	//	[WebView loadHTMLString:HTMLData baseURL:[NSURL fileURLWithPath:[[NSBundle mainBundle] bundlePath]]];
	
	NSString *urlAddress=@"http://wangjun.easymorse.com";
	NSURL *url=[NSURL URLWithString:urlAddress];
	NSURLRequest *resquestobj=[NSURLRequest requestWithURL:url];
	[WebView loadRequest:resquestobj];
	
	
}



/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	[WebView release];
    [super dealloc];
}

@end
