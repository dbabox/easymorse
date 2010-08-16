//
//  MyDataSource.m
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "MyDataSource.h"
#import "JSON.h"

@implementation MyDataSource
+ (NSDictionary *)fetchLibraryInformation
{
    NSString *urlString = [NSString stringWithFormat:@"http://wangjun.easymorse.com/wp-content/video/hello.jison"];
    NSURL *url = [NSURL URLWithString:urlString];
	NSLog(@"fetching library data");
    return [self fetchJSONValueForURL:url];
}

+ (id)fetchJSONValueForURL:(NSURL *)url
{
    NSString *jsonString = [[NSString alloc] initWithContentsOfURL:url
                                                          encoding:NSUTF8StringEncoding error:nil];
	
    id jsonValue = [jsonString JSONValue];
    [jsonString release];
    return jsonValue;
}

@end
