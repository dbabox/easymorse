//
//  MyCell.h
//  TableCellDemo
//
//  Created by wangjun on 10-9-20.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MyCell : UITableViewCell {
	
	IBOutlet UILabel *lable;

}
@property (nonatomic,retain)  UILabel *lable;
@end
