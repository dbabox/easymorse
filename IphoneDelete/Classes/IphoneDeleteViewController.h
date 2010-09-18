//
//  IphoneDeleteViewController.h
//  IphoneDelete
//
//  Created by wangjun on 10-9-18.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NoteDelegate.h"
@interface IphoneDeleteViewController : UIViewController <NoteDelegate>{
IBOutlet UITextField *textView;
}
@property (nonatomic,retain) UITextField *textView;
@end

