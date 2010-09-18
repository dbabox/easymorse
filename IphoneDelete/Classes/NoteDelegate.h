//
//  NoteDelegate.h
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//  定义协议
#import <UIKit/UIKit.h>
@protocol NoteDelegate
//回调函数
-(void)messageCallBack:(NSString *)string;
@end
