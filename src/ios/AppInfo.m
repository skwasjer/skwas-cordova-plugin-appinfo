/*
The MIT License (MIT)

Copyright (c) 2015 skwas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/


#include <sys/types.h>
#include <sys/sysctl.h>

#import <Cordova/CDV.h>
#import "AppInfo.h"

@interface AppInfo () {}
@end

@implementation AppInfo

- (void)getAppInfo:(CDVInvokedUrlCommand*)command
{
    NSDictionary* appProperties = [self appProperties];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:appProperties];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSDictionary*)appProperties
{
    NSMutableDictionary* appProps = [NSMutableDictionary dictionaryWithCapacity:4];

	// Get app name and version.
    NSDictionary *info = [[NSBundle mainBundle] infoDictionary];
    [appProps setObject:[info objectForKey:@"CFBundleName"] forKey:@"name"];
    [appProps setObject:[info objectForKey:@"CFBundleShortVersionString"] forKey:@"version"];


	
	// Get compile date.
	NSString *compileDate = [NSString stringWithUTF8String:__DATE__];
    NSDateFormatter *df = [[[NSDateFormatter alloc] init] autorelease];
    [df setDateFormat:@"MMM d yyyy"];
    NSLocale *usLocale = [[NSLocale alloc] initWithLocaleIdentifier:@"en_US"];
    [df setLocale:usLocale];
    [usLocale release];
    NSDate *aDate = [df dateFromString:compileDate];
    
    
    NSDateFormatter *df2 = [[[NSDateFormatter alloc] init] autorelease];
    [df2 setDateFormat:@"dd-MM-yyyy"];
    
    NSString *formattedDateString =
        [NSString stringWithFormat:@"%@ %s" , [df2 stringFromDate:aDate], __TIME__];
    
    [appProps setObject:formattedDateString forKey:@"compileDate"];

	// Always HW accelerated.
    [appProps setObject:[NSNumber numberWithBool:TRUE] forKey:@"isHardwareAccelerated"];

	// Debuggable based on build config.
#if DEBUG
    [appProps setObject:[NSNumber numberWithBool:TRUE] forKey:@"isDebuggable"];
#else
    [appProps setObject:[NSNumber numberWithBool:FALSE] forKey:@"isDebuggable"];
#endif
	
	
    NSDictionary* appReturn = [NSDictionary dictionaryWithDictionary:appProps];
    return appReturn;
}

@end
