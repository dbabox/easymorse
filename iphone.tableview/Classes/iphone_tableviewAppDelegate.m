//
//  iphone_tableviewAppDelegate.m
//  iphone.tableview
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "iphone_tableviewAppDelegate.h"
#import "iphone_tableviewViewController.h"

@implementation iphone_tableviewAppDelegate

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
