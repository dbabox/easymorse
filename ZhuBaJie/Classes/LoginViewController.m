//
//  LoginViewController.m
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "LoginViewController.h"


@implementation LoginViewController

@synthesize username;
@synthesize password;


-(IBAction)textFiledDoneEditing:(id)sender
{
	//关闭键盘
	[sender resignFirstResponder];
	
}
//点击空白处 关闭键盘
-(IBAction)backgroundClick:(id)sender
{
	[username resignFirstResponder];
	[password resignFirstResponder];
	
}
-(IBAction)bajielog:(id)sender
{
	[username resignFirstResponder];
	[password resignFirstResponder];
	
	NSLog(@"user=%@",username.text);
	NSLog(@"pass=%@",password.text);
	
	//	SendPostXml *sendPostXml=[[SendPostXml  alloc] init];
	//	[sendPostXml sendXml:username.text :password.text];
	//	
	//	[sendPostXml release];
	[self.view removeFromSuperview];
	
}
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	NSLog(@"登录页面");
}
/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	[username release]; 
	[password release]; 
    [super dealloc];
}


@end
