//
//  PlayMoveViewDemoViewController.m
//  PlayMoveViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "PlayMoveViewDemoViewController.h"
#import "MediaPlayer/MediaPlayer.h"

@implementation PlayMoveViewDemoViewController

@synthesize button;

-(IBAction) pressedbutton:(id)sender

{

	NSLog(@"按到我了");
	
	[self playVideo];

}

- (void)playVideo
{
	MPMoviePlayerController *moviePlayer;
	moviePlayer = [[MPMoviePlayerController alloc] initWithContentURL:[NSURL URLWithString:@"http://dev.mopietek.net:8080/mp4/320480flv.3gp"]];
	moviePlayer.movieControlMode = MPMovieControlModeDefault;
	[moviePlayer play];
	//[path release];
	NSLog(@"play~");
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
	[button release];
    [super dealloc];
}

@end
