//
//  JSONTableTestAppDelegate.h
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class JSONTableTestViewController;

@interface JSONTableTestAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    JSONTableTestViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet JSONTableTestViewController *viewController;

@end

