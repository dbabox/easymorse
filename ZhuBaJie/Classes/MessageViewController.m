//
//  MessageViewController.m
//  ZhuBaJie
//
//  Created by wangjun on 10-8-19.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//
#import "MessageViewController.h"
#import "SendMessageViewController.h"
@implementation MessageViewController
@synthesize listData;
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	NSArray *array= [[NSArray alloc] initWithObjects:@"我的消息",@"发送消息",nil];
	self.listData=array;
	[array release];
    [super viewDidLoad];
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
	[listData release];
    [super dealloc];
}
//添加行数

-(NSInteger) tableView:(UITableView *)tableView

 numberOfRowsInSection:(NSInteger)section

{
	
	return [self.listData count];
	
}

//添加每一行的信息

- (UITableViewCell *) tableView:(UITableView *)tableView

		  cellForRowAtIndexPath:(NSIndexPath *)indexPath

{
	
	static NSString *tag=@"tag";
	
	UITableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:tag];
	
	if (cell==nil) {
		
		cell=[[[UITableViewCell alloc] initWithFrame:CGRectZero
			   
									 reuseIdentifier:tag] autorelease];
		
	}
	NSUInteger row=[indexPath row];
	cell.text=[listData objectAtIndex:row];
	return cell;
	
}
//点击每行信息
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	NSUInteger row=[indexPath row];
	switch (row) {
		case 0:
			
			break;
		case 1:
			//发送消息
			[self sendMessage];
			break;
		default:
			break;
			}
}
-(void) sendMessage
{
	SendMessageViewController *sendMessageViewController=[[SendMessageViewController alloc] initWithNibName:@"sendmessage" bundle:nil];
	UINavigationBar *nav=[self navigationController];
				sendMessageViewController.hidesBottomBarWhenPushed=YES;
				[nav pushViewController:sendMessageViewController
							   animated:YES];
				[sendMessageViewController release];
}
@end