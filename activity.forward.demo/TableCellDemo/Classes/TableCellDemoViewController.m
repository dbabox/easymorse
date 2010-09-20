//
//  TableCellDemoViewController.m
//  TableCellDemo
//
//  Created by wangjun on 10-9-20.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "TableCellDemoViewController.h"
#import "MyCell.h"
@implementation TableCellDemoViewController
@synthesize tableview;
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	//self.tableview.rowHeight=40;
}
- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}
- (void)dealloc {
	[self.tableview release];
    [super dealloc];
}
-(NSInteger) tableView:(UITableView *)tableView

 numberOfRowsInSection:(NSInteger)section

{
	return 1;
	
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
	static NSString *CellIdentifier = @"CustomCellIdentifier";
	
    MyCell *cell = (MyCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		NSArray *array = [[NSBundle mainBundle] loadNibNamed:@"mycell" owner:self options:nil];
		cell = [array objectAtIndex:0];
		[cell setSelectionStyle:UITableViewCellSelectionStyleGray];
    }
	[[cell lable] setText:@"你好"];
	//[[cell imageView] setImage:[UIImage imageNamed:[imageNameArray objectAtIndex:indexPath.row]]];
	//[[cell nameLabel] setText:[nameArray objectAtIndex:indexPath.row]];
	return cell;
}
- (CGFloat)tableView:(UITableView *)atableView heightForRowAtIndexPath:(NSIndexPath *)indexPath   

{       
	return 90;
	
}
@end
