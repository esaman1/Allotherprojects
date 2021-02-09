//
//  addNewAddressView.swift
//  cryptMe
//
//  Created by Ints Graveris on 30/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//


import Foundation
import UIKit


class addNewAddressView: UIViewController {
    
    @IBOutlet var typeBtn: UIButton!
    
    @IBOutlet var cancelBtn: UIButton!
    @IBOutlet var alertView: UIView!
   
    var delegate: addNewAddressViewDelegate?
    let alertViewGrayColor = UIColor(red: 224.0/255.0, green: 224.0/255.0, blue: 224.0/255.0, alpha: 1)
    let cryptosToReceive = ["BTC", "LTC", "ETH", "DASH"]
    
    @IBOutlet var addressErrorLbl: UILabel!
    
    @IBOutlet var addressTxt: UITextField!
    
    var selectedTypeCoin = "ETH"
    var selectedAddress = "address"
    override func viewDidLoad() {
        super.viewDidLoad()
        
        addressErrorLbl.isHidden = true
       // cancelBtn.backgroundColor = .clear
        cancelBtn.layer.cornerRadius = 20
        cancelBtn.layer.borderWidth = 1
        cancelBtn.layer.borderColor = UIColor.white.cgColor
        self.tabBarController?.tabBar.isHidden = true
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setupView()
        animateView()
        self.tabBarController?.tabBar.isHidden = true

    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        view.layoutIfNeeded()
        
    }
    
    func setupView() {
        alertView.layer.cornerRadius = 15
        self.view.backgroundColor = UIColor.black.withAlphaComponent(0.9)
    }
    
    
    func animateView() {
        alertView.alpha = 0;
        self.alertView.frame.origin.y = self.alertView.frame.origin.y + 50
        UIView.animate(withDuration: 0.4, animations: { () -> Void in
            self.alertView.alpha = 1.0;
            self.alertView.frame.origin.y = self.alertView.frame.origin.y - 50
        })
    }
    
    @IBAction func selectAddressType(_ sender: UIButton) {
        
       let cryptosToReceive = ["BTC", "LTC", "ETH", "DASH"]
        let otherAlert = UIAlertController(title: "Select Crypto name", message: "After selecting type of crypto name, just type address to share with friends", preferredStyle: UIAlertController.Style.actionSheet)

        let btcBtn = UIAlertAction(title: "BTC", style: UIAlertAction.Style.default) { _ in
            self.typeBtn.setTitle("BTC", for: .normal)
          }
        let ethBtn = UIAlertAction(title: "ETH", style: UIAlertAction.Style.default) { _ in
          self.typeBtn.setTitle("ETH", for: .normal)
        }
        let ltcBtn = UIAlertAction(title: "LTC", style: UIAlertAction.Style.default) { _ in
          self.typeBtn.setTitle("LTC", for: .normal)
        }
        let dashBtn = UIAlertAction(title: "DASH", style: UIAlertAction.Style.default) { _ in
          self.typeBtn.setTitle("DASH", for: .normal)
        }
        
        let cancel = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)


          // relate actions to controllers
            otherAlert.addAction(btcBtn)
            otherAlert.addAction(ethBtn)
            otherAlert.addAction(ltcBtn)
            otherAlert.addAction(dashBtn)
            otherAlert.addAction(cancel)

        present(otherAlert, animated: true, completion: nil)
        
        
    }
    
    
    
    func doSomething(action: UIAlertAction) {
    }
    
    @IBAction func cancelBtnAction(_ sender: Any) {
        delegate?.cancelButtonTapped()
        dismiss(animated: true, completion: nil)
    }
    
    
    
    @IBAction func saveBtnAction(_ sender: Any) {
        
        if addressTxt.text!.count < 5 {
            addressErrorLbl.isHidden = false
            addressErrorLbl.text = "Add correct crypto address to continue"
            return
        }
        
        delegate?.saveBtnTapped(type: typeBtn.titleLabel!.text!, address: addressTxt.text!)
        
        
        dismiss(animated: true, completion: nil)

    }
    
    
}



