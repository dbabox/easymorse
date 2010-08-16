//
//  MyDataSource.m
//  JSONTableTest
//
//  Created by wangjun on 10-8-13.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "MyDataSource.h"


@implementation MyDataSource

#if USE_APPLICATION_UNIT_TEST     // all code under test is in the iPhone Application

- (void) testAppDelegate {
    
    id yourApplicationDelegate = [[UIApplication sharedApplication] delegate];
    STAssertNotNil(yourApplicationDelegate, @"UIApplication failed to find the AppDelegate");
    
}

#else                           // all code under test must be linked into the Unit Test bundle

- (void) testMath {
    
    STAssertTrue((1+1)==2, @"Compiler isn't feeling well today :-(" );
    
}


#endif


@end
