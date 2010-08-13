//
//  iphone_tablecellViewController.h
//  iphone.tablecell
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#define kNameValueTag  1
#define kColorValuetag 2
@interface iphone_tablecellViewController : UIViewController 
<UITableViewDelegate,UITableViewDataSource>{
	
	NSArray *computers;

}

@property (nonatomic,retain) NSArray *computers;

@end

