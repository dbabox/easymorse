//
//  helloAppDelegate.h
//  hello
//
//  Created by Marshal Wu on 10-9-11.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class helloViewController;

@interface helloAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    helloViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet helloViewController *viewController;

@end

