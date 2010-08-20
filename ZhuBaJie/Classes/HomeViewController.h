//
//  HomeViewController.h
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@interface HomeViewController : UIViewController 
<UITableViewDelegate,UITableViewDataSource>{
	NSArray *listData;
}
@property (nonatomic,retain) NSArray *listData;
@end

