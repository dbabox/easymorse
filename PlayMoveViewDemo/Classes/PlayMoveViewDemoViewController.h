//
//  PlayMoveViewDemoViewController.h
//  PlayMoveViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PlayMoveViewDemoViewController : UIViewController {
	
	IBOutlet UIButton *button;

}

@property (nonatomic,retain)UIButton *button;

-(IBAction) pressedbutton:(id)sender;

@end

