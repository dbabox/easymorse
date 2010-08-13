//
//  iphone_tablecellViewController.m
//  iphone.tablecell
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "iphone_tablecellViewController.h"

@implementation iphone_tablecellViewController

@synthesize computers;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    //[super viewDidLoad];
	//初始化数据
	NSDictionary *row1=[[NSDictionary alloc] initWithObjectsAndKeys:
						@"王军",@"姓名",@"男",@"性别",nil];
	NSDictionary *row2=[[NSDictionary alloc] initWithObjectsAndKeys:
						@"大辉",@"姓名",@"男",@"性别",nil];
	NSDictionary *row3=[[NSDictionary alloc] initWithObjectsAndKeys:
						@"王鑫",@"姓名",@"男",@"性别",nil];
	NSArray *array=[[NSArray alloc] initWithObjects:row1,row2,row3,nil];
	self.computers=array;
	
	[row1 release];
	[row2 release];
	[row3 release];
	[array release];
}

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
	[computers release];
    [super dealloc];
}

//添加行数
-(NSInteger) tableView:(UITableView *)tableView
 numberOfRowsInSection:(NSInteger)section
{
	
	return [self.computers count];
	
}
//添加每一行的信息
- (UITableViewCell *) tableView:(UITableView *)tableView
		  cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
	static NSString *tag=@"tag";
	
	UITableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:tag];
	if (cell==nil) {
		CGRect cellFrom=CGRectMake(0, 0, 300, 65);
		cell=[[[UITableViewCell alloc] initWithFrame:cellFrom
			    reuseIdentifier:tag] autorelease];
		
		CGRect nameLabelRect=CGRectMake(0, 5, 70, 15);
		UILabel *nameLabel=[[UILabel alloc] initWithFrame:nameLabelRect];
		nameLabel.textAlignment=UITextAlignmentRight;
		nameLabel.text=@"姓名";
		nameLabel.font=[UIFont boldSystemFontOfSize:12];
		[cell.contentView addSubview:nameLabel];
		[nameLabel release];
		
		CGRect colorLabelRect=CGRectMake(0, 26, 70, 15);
		UILabel *colorLabel=[[UILabel alloc] initWithFrame:colorLabelRect];
		colorLabel.textAlignment=UITextAlignmentRight;
		colorLabel.text=@"性别";
		colorLabel.font=[UIFont boldSystemFontOfSize:12];
		[cell.contentView addSubview:colorLabel];
		[colorLabel release];
		
		CGRect nameValueLabelRect=CGRectMake(80, 5, 200, 15);
		UILabel *nameValue=[[UILabel alloc] initWithFrame:nameValueLabelRect];
		nameValue.tag=kNameValueTag;
		[cell.contentView addSubview:nameValue];
		[nameValue release];
		
		
		CGRect colorValueLabelRect=CGRectMake(80, 25, 200, 15);
		UILabel *colorValue=[[UILabel alloc] initWithFrame:colorValueLabelRect];
		colorValue.tag=kColorValuetag;
		[cell.contentView addSubview:colorValue];
		[colorValue release];
	}
	
	NSUInteger row=[indexPath row];
	NSDictionary *rowData=[self.computers objectAtIndex:row];
	UILabel *name=(UILabel *)[cell.contentView viewWithTag:kNameValueTag];
	name.text=[rowData objectForKey:@"姓名"];
	UILabel *color=(UILabel *)[cell.contentView viewWithTag:kColorValuetag];
	color.text=[rowData objectForKey:@"性别"];

	UIImage *image=[UIImage imageNamed:@"ceshi.png"];
	
	cell.image=image;
	
	return cell;
	
}

@end
