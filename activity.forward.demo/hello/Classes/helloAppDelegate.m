//
//  helloAppDelegate.m
//  hello
//
//  Created by Marshal Wu on 10-9-11.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "helloAppDelegate.h"
#import "helloViewController.h"

@implementation helloAppDelegate

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
