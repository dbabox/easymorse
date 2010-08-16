//
//  DisclosureDetailController.m
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DisclosureDetailController.h"


@implementation DisclosureDetailController

@synthesize lable;
@synthesize message;

 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
-(void) viewWillAppear:(BOOL)animated{

	lable.text=message;
	[super viewWillAppear:animated];

}

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
	[lable release];
	[message release];
    [super dealloc];
}


@end
