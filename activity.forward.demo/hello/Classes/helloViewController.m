//
//  helloViewController.m
//  hello
//
//  Created by Marshal Wu on 10-9-11.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "helloViewController.h"

@implementation helloViewController
@synthesize text;


// Implement loadView to create a view hierarchy programmatically, without using a nib.
//- (void)loadView {
//	NSString* content= [[NSString alloc] initWithFormat:@"aaa"];
//	text.text=content;
//	[content release];
//}

- (IBAction)buttonPressed:(id)sender{
	NSString* content= [[NSString alloc] initWithFormat:@"来自%@按钮",[sender titleForState:UIControlStateNormal]];
	text.text=content;
	[content release];
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
	[text release];
    [super dealloc];
}

@end
