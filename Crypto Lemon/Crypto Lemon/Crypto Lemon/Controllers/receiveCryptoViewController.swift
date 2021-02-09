//
//  receiveCryptoViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 30/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import RealmSwift

class receiveCryptoViewController: UIViewController, addNewAddressViewDelegate, UITableViewDataSource, UITableViewDelegate {
    
    
    @IBOutlet var receiveTbl: UITableView!
    @IBOutlet var addressLbl: UILabel!
    
    @IBOutlet var amountTxt: UITextField!
    
    @IBOutlet var selectAddressBtn: UIButton!
    @IBOutlet weak var imgQRCode: UIImageView!

    var addressToShare = "We are sharing address"
    var amountToShare = "0.00"
    var addressType = ""
    let realm = try? Realm()
    var addressRecord: Results<Address> {
        get {
            return realm!.objects(Address.self)
        }
    }
    
    var selectedAddress = ""
    var qrcodeImage: CIImage!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        getAllRealmRecords()

        if let fileUrl = Realm.Configuration.defaultConfiguration.fileURL{
            print("FILE URL IS",fileUrl)
        }
        
        receiveTbl.delegate = self
        receiveTbl.dataSource = self
        
        receiveTbl.isHidden = true
        
        getAllRealmRecords()
        
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getAllRealmRecords()
        if addressRecord.count > 0 {
            addressLbl.text = addressRecord[0].addressName
            let image = self.generateQRCode(from: "\(self.addressRecord[0].addressName)")
            self.imgQRCode.image = image

        }

    }
    func getAllRealmRecords() {
        
        let sortRecords = realm?.objects(Address.self).sorted(byKeyPath: "addressID")
        print("counted records", sortRecords?.count)
        for findRecords in sortRecords! {
            print(findRecords.addressType)
         //   realmDbCoin.append(findRecords.coinFullName)
            
        }
        
       // getCryptos()
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
             
             
             return addressRecord.count
         }
    
         func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            
             let cell = receiveTbl.dequeueReusableCell(withIdentifier: "receiveAddressTableViewCell", for: indexPath) as! receiveAddressTableViewCell
             cell.selectionStyle = .none

            
            var showRecords = addressRecord[indexPath.row]
            if showRecords.addressType == "BTC" {
                cell.addressImg.image = UIImage(named: "bitcoin")
                
            } else if showRecords.addressType == "ETH" {
                cell.addressImg.image = UIImage(named: "ethereum")
                
            }  else if showRecords.addressType == "DASH" {
                cell.addressImg.image = UIImage(named: "dash")
                
            } else if showRecords.addressType == "LTC" {
                cell.addressImg.image = UIImage(named: "litecoin")

            }
            cell.addressLbl.text = showRecords.addressName
             
             return cell
         }
         
         func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
     
            var showRecords = addressRecord[indexPath.row]

            selectedAddress = showRecords.addressName
            addressType = showRecords.addressType
            addressLbl.text = selectedAddress
            let image = self.generateQRCode(from: "\(self.selectedAddress)")
            self.imgQRCode.image = image
            
            
            receiveTbl.isHidden = true
                
                 
             
             
         }
         
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
             
             return 58
         }
    
    
    @IBAction func selectAddress(_ sender: UIButton) {
        
        receiveTbl.isHidden = false
        
        
    }
    
    @IBAction func addNewAddress(_ sender: UIButton) {
        
        let customAlert = self.storyboard?.instantiateViewController(withIdentifier: "addNewAddressView") as! addNewAddressView
        customAlert.providesPresentationContextTransitionStyle = true
        customAlert.definesPresentationContext = true
        customAlert.modalPresentationStyle = UIModalPresentationStyle.overCurrentContext
        customAlert.modalTransitionStyle = UIModalTransitionStyle.crossDissolve
        customAlert.delegate = self as? addNewAddressViewDelegate
        self.present(customAlert, animated: true, completion: nil)
        
    }
    
    @IBAction func shareSocial(_ sender: UIButton) {
        
        var shortName = ""
        amountToShare = amountTxt.text!
        addressToShare = selectedAddress
        if addressType == "BTC" {
            
            shortName = "bitcoin"
        } else if addressType == "ETH" {
            shortName = "ethereum"

        }  else if addressType == "DASH" {
            shortName = "dash"

        } else if addressType == "LTC" {
            shortName = "litecoin"

        }
        
        displayShareSheet(shareContent: "Please send \(amountToShare) \(addressType) to this address. \(shortName)://\(addressToShare)?=\(amountToShare)")
    }
    
    func displayAlert(title: String, message: String) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        present(alertController, animated: true, completion: nil)
        return
    }
    
    func displayShareSheet(shareContent:String) {
        let activityViewController = UIActivityViewController(activityItems: [shareContent as NSString], applicationActivities: nil)
        present(activityViewController, animated: true, completion: {})
    }
    
    
    func saveBtnTapped(type: String, address: String) {
        
        print(type, address)
    
        let realm = try? Realm()
        var newAddress = Address()
        
        
        newAddress = Address.init(addressID: newAddress.incrementID(), addressType: type, addressName: address)
        
        try! realm?.write {
            realm?.add(newAddress)
        }
        
        showToast(message: "You succesfully added new address")
        getAllRealmRecords()
 
        
    }
    
    func cancelButtonTapped() {
        print("USER CANCELED")
    }
    @IBAction func copyAddress(_ sender: UIButton) {
        
        addressToShare = selectedAddress
        UIPasteboard.general.string = addressToShare

        
       }
    
    func generateQRCode(from string: String) -> UIImage? {
        let data = string.data(using: String.Encoding.ascii)
        
        if let filter = CIFilter(name: "CIQRCodeGenerator") {
            filter.setValue(data, forKey: "inputMessage")
            let transform = CGAffineTransform(scaleX: 10, y: 10)
        
            
            if let output = filter.outputImage?.transformed(by: transform) {
                return UIImage(ciImage: output)
            }
        }
        
        return nil
    }
    
    func displayQRCodeImage() {
        let scaleX = imgQRCode.frame.size.width / qrcodeImage.extent.size.width
        let scaleY = imgQRCode.frame.size.height / qrcodeImage.extent.size.height
        
        let transformedImage = qrcodeImage.transformed(by: CGAffineTransform(scaleX: scaleX, y: scaleY))
        
      //  imgQRCode.image = UIImage(CIImage: transformedImage)
        imgQRCode.image = UIImage(ciImage: transformedImage)
    }
    
    @IBAction func goToSettings(_ sender: Any) {
        
        
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .white
        let toSettings = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "settingsViewController") as! settingsViewController
        self.navigationController?.pushViewController(toSettings, animated: true)
        
    }
    
    func showToast(message : String) {
           
           let toastLabel = UILabel(frame: CGRect(x: self.view.frame.size.width/2 - 100, y: self.view.frame.size.height-100, width: 200, height: 36))
           toastLabel.backgroundColor = #colorLiteral(red: 0.9607843137, green: 0.9254901961, blue: 0, alpha: 1)
           toastLabel.textColor = #colorLiteral(red: 0.01633078046, green: 0.01955444925, blue: 0.04027635604, alpha: 1)//UIColor.white
           toastLabel.textAlignment = .center;
           toastLabel.font = UIFont(name: "Geeza Pro", size: 12.0)
           toastLabel.text = message
           toastLabel.alpha = 1.0
           toastLabel.layer.cornerRadius = 18;
           toastLabel.clipsToBounds  =  true
           self.view.addSubview(toastLabel)
           UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
               toastLabel.alpha = 0.0
           }, completion: {(isCompleted) in
               toastLabel.removeFromSuperview()
           })
       }
}
