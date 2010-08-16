//
//  NavAppDelegate.h
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NavAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	IBOutlet UINavigationController  *navController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic,retain) UINavigationController  *navController;

@end

