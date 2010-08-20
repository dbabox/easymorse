//
//  SendMessageViewController.h
//  ZhuBaJie
//
//  Created by wangjun on 10-8-20.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import <UIKit/UIKit.h>


@interface SendMessageViewController : UIViewController {
	
	IBOutlet UITextField *name;
	IBOutlet UITextField *message;

}
@property (nonatomic,retain) UITextField *name;
@property (nonatomic,retain) UITextField *message;
-(IBAction)backgroundClick:(id)sender;
@end
