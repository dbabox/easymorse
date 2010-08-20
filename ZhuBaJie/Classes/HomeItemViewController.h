//
//  HomeItemViewController.h
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface HomeItemViewController : UIViewController {

	IBOutlet UITabBar *tabBar;
	
}

@property (nonatomic,retain)  UITabBar *tabBar;
-(IBAction) buttonClickedpress:(id)sender;
@end
