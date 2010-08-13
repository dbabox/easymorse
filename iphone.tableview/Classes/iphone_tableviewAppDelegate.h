//
//  iphone_tableviewAppDelegate.h
//  iphone.tableview
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class iphone_tableviewViewController;

@interface iphone_tableviewAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    iphone_tableviewViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet iphone_tableviewViewController *viewController;

@end

