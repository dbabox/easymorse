//
//  ManagerMessage.h
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NoteDelegate.h"
@interface ManagerMessage : NSObject {
	id<NoteDelegate> *noteDelegate;
}
@property (nonatomic,retain) id<NoteDelegate> *noteDelegate;
-(void)startThread;
@end
