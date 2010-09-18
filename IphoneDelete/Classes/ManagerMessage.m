//
//  ManagerMessage.m
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import "ManagerMessage.h"
@implementation ManagerMessage
@synthesize noteDelegate;
//开始一个线程
 -(void)startThread
{

	[NSTimer scheduledTimerWithTimeInterval:3
									 target:self
								   selector:@selector(targetMethod:)
								   userInfo:nil
									repeats:NO];
}
-(void)targetMethod:(NSString *)string
{
	if (self.noteDelegate!=nil) {
		//完成线程 调用回调函数
		[self.noteDelegate messageCallBack:@"回调函数"];
		}
}
@end
