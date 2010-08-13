//
//  iphone_tablecellAppDelegate.m
//  iphone.tablecell
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "iphone_tablecellAppDelegate.h"
#import "iphone_tablecellViewController.h"

@implementation iphone_tablecellAppDelegate

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
