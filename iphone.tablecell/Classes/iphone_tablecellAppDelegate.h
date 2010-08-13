//
//  iphone_tablecellAppDelegate.h
//  iphone.tablecell
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class iphone_tablecellViewController;

@interface iphone_tablecellAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    iphone_tablecellViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet iphone_tablecellViewController *viewController;

@end

