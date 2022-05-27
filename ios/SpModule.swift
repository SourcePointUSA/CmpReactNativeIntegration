//
//  SpModule.swift
//  CmpReactNativeIntegration
//
//  Created by Sourcepoint
//

import Foundation
import ConsentViewController

@objc(SpModule)
class SpModule : NSObject {

  @objc static func requiresMainQueueSetup() -> Bool {
        return false
    }
  
  @objc
  func clearData() -> Void {
    SPConsentManager.clearAllData()
  }
  
  @objc
  func showCcpaPm() -> Void {
    NotificationCenter.default.post(
      name: UIApplication.willEnterForegroundNotification,
      object: "ccpa"
    )
  }
  
  @objc
  func showGdprPm() -> Void {
    NotificationCenter.default.post(
      name: UIApplication.willEnterForegroundNotification,
      object: "gdpr"
    )
  }
}
