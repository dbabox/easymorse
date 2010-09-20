//
//  TableCellDemoAppDelegate.h
//  TableCellDemo
//
//  Created by wangjun on 10-9-20.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TableCellDemoViewController;

@interface TableCellDemoAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    TableCellDemoViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet TableCellDemoViewController *viewController;

@end

