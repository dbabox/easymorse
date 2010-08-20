//
//  LoginViewController.h
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface LoginViewController : UIViewController {
	IBOutlet UITextField *username;
	IBOutlet UITextField *password;
}

@property (nonatomic,retain) UITextField *username;
@property (nonatomic,retain) UITextField *password;

-(IBAction)textFiledDoneEditing:(id)sender;
-(IBAction)backgroundClick:(id)sender;
-(IBAction)bajielog:(id)sender;


@end
