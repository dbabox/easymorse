//
//  PlayMoveViewDemoAppDelegate.h
//  PlayMoveViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PlayMoveViewDemoViewController;

@interface PlayMoveViewDemoAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    PlayMoveViewDemoViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet PlayMoveViewDemoViewController *viewController;

@end

