//
//  JSONTableTestViewController.h
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JSONTableTestViewController : UIViewController 
<UITableViewDelegate,UITableViewDataSource>{
	
	NSDictionary *myData;

}

@property (nonatomic,retain) NSDictionary *myData;

@end

