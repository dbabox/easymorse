//
//  MyDataSource.h
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface MyDataSource : NSObject {

}
//+ (id)dataSource;
+ (NSDictionary *)fetchLibraryInformation;
+ (id)fetchJSONValueForURL:(NSURL *)url;
@end
