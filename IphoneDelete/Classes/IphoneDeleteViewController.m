//
//  IphoneDeleteViewController.m
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "IphoneDeleteViewController.h"
#import "ManagerMessage.h"
@implementation IphoneDeleteViewController
@synthesize textView;

//回调函数
-(void)messageCallBack:(NSString *)string
{
	self.textView.text=string;
}

- (void)viewDidLoad {
    [super viewDidLoad];
	self.textView.text=@"测试";
	ManagerMessage *message=[[ManagerMessage alloc] init];
	//通知调用协议
	message.noteDelegate=self;
    [message startThread];
	[message release];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
	self.textView=nil;
}


- (void)dealloc {
	[self.textView release];
    [super dealloc];
}

@end
