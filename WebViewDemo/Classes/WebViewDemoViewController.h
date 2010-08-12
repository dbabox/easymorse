//
//  WebViewDemoViewController.h
//  WebViewDemo
//
//  Created by wangjun on 10-8-12.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WebViewDemoViewController : UIViewController {
	
	IBOutlet UIWebView *WebView;
	IBOutlet UINavigationBar *Nav;
}

@property (nonatomic,retain) UIWebView *WebView;

@end

