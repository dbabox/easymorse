//
//  DisclosureButtonController.m
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DisclosureButtonController.h"
#import "NavAppDelegate.h"
#import "DisclosureDetailController.h"

@implementation DisclosureButtonController

@synthesize list;

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

-(void)viewDidLoad{
	
	NSArray *array=[[NSArray alloc] initWithObjects:@"Toy Story",@"A Bug's Life",
					@"Toy Story 2",@"Monsters,Inc.",@"Finding Nemo",@"The Incredibles",@"Cars",
					@"Ratatouille",@"WALL-E",@"Up",nil];
	self.list=array;
	[array release];
	[super viewDidLoad];
	
}


- (void)dealloc {
	[list release];
	[childController release];
    [super dealloc];
}



//添加行数

-(NSInteger) tableView:(UITableView *)tableView

 numberOfRowsInSection:(NSInteger)section

{
	
	return [list count];
	
}

//添加每一行的信息

- (UITableViewCell *) tableView:(UITableView *)tableView

		  cellForRowAtIndexPath:(NSIndexPath *)indexPath

{
	
	static NSString *tag=@"DisclosureButtonController";
	
	UITableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:tag];
	
	if (cell==nil) {
		
		cell=[[[UITableViewCell alloc] initWithFrame:CGRectZero
			   
									 reuseIdentifier:tag] autorelease];
		
	}
	
	//添加每一行的信息
	
	NSUInteger row=[indexPath row];
	NSString *rowString =[list objectAtIndex:row];
	cell.text=rowString;
	[rowString release];
	return cell;
	
}

//展示按钮
-(UITableViewCellAccessoryType)tableView:(UITableView *)tableView
		accessoryTypeForRowWithIndexPath:(NSInteger *)indexPath
{
	
	return UITableViewCellAccessoryDetailDisclosureButton;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	
	UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"你选择的按钮？"
												 message:@"按下按钮"
												delegate:nil
									   cancelButtonTitle:@"重新开始" 
									   otherButtonTitles:nil];
	[alert show];
	[alert release];
	
}
//显示细节
- (void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath
{

	if (childController==nil) 
		childController=[[DisclosureDetailController alloc]
						 initWithNibName:@"DisclosureDetail" bundle:nil];
		
		childController.title=@"Disclosure Button Pressed";
		NSUInteger row=[indexPath row];
		
		NSString *selectedMovie=[list objectAtIndex:row];
		NSString *detailMessage=[[NSString alloc]
								 initWithFormat:@"You pressed the disclosure button for %@.",selectedMovie];
		childController.message=detailMessage;
		childController.title=selectedMovie;
		[detailMessage release];
		NavAppDelegate *delegate=[[UIApplication sharedApplication]delegate];
		[delegate.navController pushViewController:childController
										  animated:YES];

}

@end
