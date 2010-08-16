//
//  RootViewController.m
//  Nav
//
//  Created by wangjun on 10-8-16.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "RootViewController.h"
#import "SecondLevelViewController.h"
#import "NavAppDelegate.h"
#import "DisclosureButtonController.h"

@implementation RootViewController

@synthesize controllers;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	self.title=@"Root Level";
	NSMutableArray *array=[[NSMutableArray alloc] init];
	DisclosureButtonController *disclosureButtonController=
	[[DisclosureButtonController alloc]initWithStyle:UITableViewStylePlain];
	disclosureButtonController.title=@"Disclouser Buttons";
	disclosureButtonController.rowImage=[UIImage 
										  imageNamed:@"ceshi.png"];
										 [array addObject:disclosureButtonController];
										 [disclosureButtonController release];
	self.controllers=array;
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
	[controllers release];
    [super dealloc];
}

//添加行数

-(NSInteger) tableView:(UITableView *)tableView

 numberOfRowsInSection:(NSInteger)section

{
	
	return [self.controllers count];
	
}

//添加每一行的信息

- (UITableViewCell *) tableView:(UITableView *)tableView

		  cellForRowAtIndexPath:(NSIndexPath *)indexPath

{
	
	static NSString *tag=@"RootViewControllerCell";
	
	UITableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:tag];
	
	if (cell==nil) {
		
		cell=[[[UITableViewCell alloc] initWithFrame:CGRectZero
			   
									 reuseIdentifier:tag] autorelease];
		
	}
	
	//添加每一行的信息
	
	NSUInteger row=[indexPath row];
	
	SecondLevelViewController *controller=[controllers objectAtIndex:row];
	
	cell.text=controller.title;
	cell.image=controller.rowImage;
	
	return cell;
	
}

-(UITableViewCellAccessoryType)tableView:(UITableView *)tableView
accessoryTypeForRowWithIndexPath:(NSInteger *)indexPath
{

	return UITableViewCellAccessoryDisclosureIndicator;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	NSUInteger row=[indexPath row];
	SecondLevelViewController *nextController=[self.controllers  objectAtIndex:row];
	
	NavAppDelegate *delegate=[[UIApplication sharedApplication]delegate];
	
	[delegate.navController pushViewController:nextController
									  animated:YES];

}


@end
