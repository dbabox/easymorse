//
//  JSONTableTestViewController.m
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "JSONTableTestViewController.h"
#import "MyDataSource.h"

@implementation JSONTableTestViewController

@synthesize myData;
- (void)viewDidLoad {
	NSLog(@"加载数据");
	myData = [[MyDataSource fetchLibraryInformation] retain];
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

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return [myData count];    //有多少个section，也就是“几家”
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [[myData valueForKey:[[myData allKeys] objectAtIndex:section]] count];
	//这里我们需要告诉UITableViewController每个section里面有几个，也就是“一家里面有几口人”
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault
									   reuseIdentifier:CellIdentifier] autorelease];
    }
	//上面的东西都是重复白给的，平时没事不用想为什么，照抄就可以了
	cell.textLabel.text = [[myData valueForKey:[[myData allKeys] objectAtIndex:indexPath.section]] objectAtIndex:indexPath.row];
	//这句看上去复杂，但是其实不过是在特定section里面找到对应的array，
	//然后在array中找到indexPath.row所在的内容
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
	return [[myData allKeys] objectAtIndex:section];
	//这里设置对应section的名字，很简单allKey返回所有的键值为一个array，也就是“张家”，“李家”
	//然后用objectAtIndex: 来找出究竟是哪一个就可以了！
}

- (void)dealloc {
  [myData release]; 
    [super dealloc];
}

@end
