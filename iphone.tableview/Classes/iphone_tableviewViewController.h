//
//  iphone_tableviewViewController.h
//  iphone.tableview
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface iphone_tableviewViewController : UIViewController 
<UITableViewDelegate,UITableViewDataSource>{
	
	NSArray *listData;

}
@property (nonatomic,retain) NSArray *listData;

@end

