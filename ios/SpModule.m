//
//  SpModule.m
//  CmpReactNativeIntegration
//
//  Created by Sourcepoint
//

#import <Foundation/Foundation.h>

#import "React/RCTBridgeModule.h"
#import <React/RCTUtils.h>
@interface RCT_EXTERN_MODULE(SpModule, NSObject)

RCT_EXTERN_METHOD(showGdprPm)
RCT_EXTERN_METHOD(clearData)
RCT_EXTERN_METHOD(showCcpaPm)

@end
