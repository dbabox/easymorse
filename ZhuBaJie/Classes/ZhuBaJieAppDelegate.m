//
//  ZhuBaJieAppDelegate.m
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "ZhuBaJieAppDelegate.h"
#import "LoginViewController.h"
@implementation ZhuBaJieAppDelegate

@synthesize window;
@synthesize loginViewController;
@synthesize rootBarController;
- (void)applicationDidFinishLaunching:(UIApplication *)application {    

    // Override point for customization after application launch
	[window addSubview:rootBarController.view];
	[window addSubview:loginViewController.view];
    [window makeKeyAndVisible];
}
- (void)dealloc {
	[loginViewController release];
    [window release];
    [super dealloc];
}


@end
