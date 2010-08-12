//
//  WebViewDemoAppDelegate.h
//  WebViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class WebViewDemoViewController;

@interface WebViewDemoAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    WebViewDemoViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet WebViewDemoViewController *viewController;

@end

