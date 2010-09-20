//
//  MyCell.m
//  TableCellDemo
//
//  Created by wangjun on 10-9-20.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import "MyCell.h"

@implementation MyCell
@synthesize lable;
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if ((self = [super initWithStyle:style reuseIdentifier:reuseIdentifier])) {
        // Initialization code
    }
    return self;
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {

    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


- (void)dealloc {
    [super dealloc];
}


@end
