//
//  DisclosureDetailController.h
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface DisclosureDetailController : UIViewController {
	
	IBOutlet UILabel *lable;
	NSString *message;

}

@property (nonatomic,retain)UILabel *lable;
@property (nonatomic,retain)NSString *message;


@end
