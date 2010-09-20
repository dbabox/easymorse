//
//  TableCellDemoViewController.h
//  TableCellDemo
//
//  Created by wangjun on 10-9-20.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TableCellDemoViewController : UIViewController 
<UITableViewDelegate,UITableViewDataSource>{

	IBOutlet UITableView *tableview;
}
@property (nonatomic,retain)  UITableView *tableview;
@end

