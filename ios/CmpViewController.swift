//
//  CmpViewController.swift
//  CmpReactNativeIntegration
//
//  Created by Sourcepoint
//

import Foundation
import ConsentViewController

class CmpViewController: UIViewController {
  
  deinit {
    NotificationCenter.default.removeObserver(
      self,
      name: UIApplication.willEnterForegroundNotification,
      object: nil
    )
  }
  
    lazy var consentManager: SPConsentManager = { SPConsentManager(
        accountId: 22,
        propertyName: try! SPPropertyName("mobile.multicampaign.demo"),
        campaignsEnv: .Public, // optional - Public by default
        campaigns: SPCampaigns(
            gdpr: SPCampaign(), // optional
            ccpa: SPCampaign(), // optional
            ios14: SPCampaign() // optional
        ),
        delegate: self
    )}()

  override func viewDidLayoutSubviews() {
    consentManager.cleanUserDataOnError = false
    consentManager.loadMessage()
    NotificationCenter.default.addObserver(
      self,
      selector: #selector(CmpViewController.pm(notification:)),
      name: UIApplication.willEnterForegroundNotification,
      object: nil
    )
  }
  
  @objc func pm(notification: Notification){
    let type = notification.object as? String  ?? ""
    if type.contains("gdpr") {
      showGdprPm()
    }else if type.contains("ccpa"){
      showCcpaPm()
    }
  }
  
  func showGdprPm() {
      DispatchQueue.main.async {
        self.consentManager.loadGDPRPrivacyManager(withId: "488393")
      }
    }

  func showCcpaPm() {
      DispatchQueue.main.async {
        self.consentManager.loadCCPAPrivacyManager(withId: "509688")
      }
    }

}

protocol RNSPDelegate : SPDelegate{}

extension CmpViewController: RNSPDelegate {
    func onSPUIReady(_ controller: UIViewController) {
        controller.modalPresentationStyle = .overFullScreen
        present(controller, animated: true)
    }

    func onAction(_ action: SPAction, from controller: UIViewController) {
        print(action)
        action.publisherData = ["foo": "action"]
        print(action)
    }

    func onSPUIFinished(_ controller: UIViewController) {
//        updateIDFAStatusLabel()
        dismiss(animated: true)
    }

    func onConsentReady(userData: SPUserData) {
        print("onConsentReady:", userData)
//        let vendorAccepted = userData.gdpr?.consents?.vendorGrants[myVendorId]?.granted ?? false
//        updateMyVendorUI(vendorAccepted)
//        updatePMButtons(ccpaApplies: consentManager.ccpaApplies, gdprApplies: consentManager.gdprApplies)
    }

    func onSPFinished(userData: SPUserData) {
        print("SDK DONE")
    }

    func onError(error: SPError) {
        print("Something went wrong: ", error)
    }
}

// MARK: - UI methods
extension CmpViewController {
    func updateIDFAStatusLabel() {
        
    }

    func updateMyVendorUI(_ accepted: Bool) {
        
    }

    func updatePMButtons(ccpaApplies: Bool, gdprApplies: Bool) {
        
    }
}
