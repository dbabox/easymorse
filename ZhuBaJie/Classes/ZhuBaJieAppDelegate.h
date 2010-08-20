//
//  ZhuBaJieAppDelegate.h
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LoginViewController;

@interface ZhuBaJieAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	IBOutlet LoginViewController *loginViewController; 
	IBOutlet UITabBarController *rootBarController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic,retain) LoginViewController *loginViewController; 
@property (nonatomic,retain)  UITabBarController *rootBarController;
@end

