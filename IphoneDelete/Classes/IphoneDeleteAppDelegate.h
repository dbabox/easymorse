//
//  IphoneDeleteAppDelegate.h
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class IphoneDeleteViewController;

@interface IphoneDeleteAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    IphoneDeleteViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet IphoneDeleteViewController *viewController;

@end

