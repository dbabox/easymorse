//
//  WebViewDemoAppDelegate.m
//  WebViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "WebViewDemoAppDelegate.h"
#import "WebViewDemoViewController.h"

@implementation WebViewDemoAppDelegate

@synthesize window;
@synthesize viewController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
