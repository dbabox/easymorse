//
//  iphone_tableviewViewController.m
//  iphone.tableview
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "iphone_tableviewViewController.h"

@implementation iphone_tableviewViewController

@synthesize listData;

- (void)viewDidLoad {
	
	NSArray *array= [[NSArray alloc] initWithObjects:@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",@"你好",nil];
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

@end
