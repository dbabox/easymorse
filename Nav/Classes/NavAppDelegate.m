//
//  NavAppDelegate.m
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "NavAppDelegate.h"

@implementation NavAppDelegate

@synthesize window;
@synthesize navController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    

    // Override point for customization after application launch
	[window addSubview:navController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
	[navController release];
    [window release];
    [super dealloc];
}


@end
