//
//  RootViewController.h
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface RootViewController : UITableViewController
<UITableViewDelegate,UITableViewDataSource>{
	
	NSArray *controllers;

}

@property (nonatomic,retain)NSArray *controllers;

@end
