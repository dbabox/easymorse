//
//  HomeItemViewController.m
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import "HomeItemViewController.h"
#import "TenderViewController.h"
@implementation HomeItemViewController
@synthesize tabBar;
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];	
}
-(IBAction) buttonClickedpress:(id)sender
{
	UISegmentedControl *segmentedControl=(UISegmentedControl *)sender;
	NSInteger selectedSegment = segmentedControl.selectedSegmentIndex;
	switch (selectedSegment) {
		case 0:
			NSLog(@"点击了报名");
			break;
		case 1:
			NSLog(@"点击了收藏");
			break;
		case 2:
			NSLog(@"点击了评论");
			break;
		case 3:
			NSLog(@"点击了交稿");
			[self deliveryPressButton];
			break;
		default:
			break;
	}

}
-(void)deliveryPressButton
{
	TenderViewController *tenderViewController=[[TenderViewController alloc] initWithNibName:@"tender" bundle:nil];
	UINavigationBar *nav=[super navigationController];
	[nav pushViewController:tenderViewController
				   animated:YES];
	[tenderViewController release];
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
	[tabBar release];
    [super dealloc];
}
@end
