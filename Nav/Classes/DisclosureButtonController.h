//
//  DisclosureButtonController.h
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondLevelViewController.h"
#import "DisclosureDetailController.h"


@interface DisclosureButtonController : SecondLevelViewController
<UITableViewDelegate,UITableViewDataSource>{
	
	NSArray *list;
	DisclosureDetailController *childController;

}
@property (nonatomic,retain) NSArray *list;
@end
