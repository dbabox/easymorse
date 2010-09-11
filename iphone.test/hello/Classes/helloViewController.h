//
//  helloViewController.h
//  hello
//
//  Created by Marshal Wu on 10-9-11.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface helloViewController : UIViewController {
	IBOutlet UILabel *text;
}

@property (retain,nonatomic) UILabel *text;

- (IBAction)buttonPressed:(id)sender;

@end

