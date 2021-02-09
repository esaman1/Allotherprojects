//
//  exchangeViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 11/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import Foundation
import UIKit
import SwiftKeychainWrapper
import Alamofire
import RealmSwift
import RLBAlertsPickers

class exchangeViewController: UIViewController, UITextFieldDelegate, UITableViewDelegate, UITableViewDataSource {
    
    
    

    @IBOutlet weak var exchangeOfFirstTxt: UITextField!
    
    @IBOutlet weak var secondImage: UIImageView!
    @IBOutlet weak var firstImage: UIImageView!
    @IBOutlet weak var firstButton: UIButton!
    @IBOutlet var secondButton: UIButton!
    
    @IBOutlet var exchangeRateLabel: UILabel!
    @IBOutlet var sumOfExchange: UILabel!
    
    @IBOutlet weak var selectCurrencyTableView: UITableView!


    
    
    var firstCurrency = ["BTC", "ETH", "DASH", "DEXO", "LTC"]
    
    var exchangeCurrencies = ["BTC", "ETH", "LTC", "DEXO", "DASH"]
    var selectedCurrencyCode : String = ""
    
    var minAmountGroup = [[String]]()
    
    
    var currencyExchange : Double = 0.0 // globālais mainīgais
    var activeCurrency : Double = 0.0
    var myCurrency:[String] = []
    var myCurrencyId:[String] = []
    var arrayOfCurrencies: [[String]] = []
    var realArrayOfCurrencies = [Any]()
    
    var rate : Double?
    var exchangePairId :Int?
    
    enum buySell: String{
        case buy    = "buy"
        case sell   = "sell"
    }
    var pairKey = ""
    
 
    var selectedCrypto : String?   // first
    var selectedCryptoSecond : String?    //second
    
 
    var countTimes : Int = 0
    var countTimesSecond : Int = 0
    
    var secondTblClose : Bool?
    var firstTblClose : Bool?
    
    var selectedItemSecond : String?
    var onChangedRate : String?
    
    var userCurrency: String? = KeychainWrapper.standard.string(forKey: "mainCurrency")
    var userCurrencySymbol: String? = KeychainWrapper.standard.string(forKey: "mainCurrencySymbol")
    
    
    var mainPercents : Double?
    var mainSum : Double?
    override func viewDidLoad() {
        super.viewDidLoad()
        if userCurrency == nil {
            userCurrency = "usd"
            userCurrencySymbol = "$"
        }
        selectedCrypto = firstButton.titleLabel?.text
        selectedCryptoSecond = "LTC"//secondButton.titleLabel?.text
        pairKey = "buy"
        //getExchangePairs()
      //  exchangeCalculations()
        //   retrieveValues()
        //
        getCryptos()
      //  getRatesUpdates()
        firstButton.adjustsImageWhenHighlighted = false
        secondButton.adjustsImageWhenHighlighted = false
     
        exchangeOfFirstTxt.delegate = self
     
        
     
        selectCurrencyTableView.delegate = self
        selectCurrencyTableView.dataSource = self
        selectCurrencyTableView.isHidden = true
        exchangeOfFirstTxt.delegate = self
        
    
        firstButton.tag = 1
        secondButton.tag = 2
        
       
        
  
        
    }
    
    @IBAction func selectCurrency(_ sender: UIButton) {
        
          firstButton.isSelected = true
              secondButton.isSelected = false
              let sellArray = ["DEXO","ETH"]
              
              if countTimes < 1 {
                  //   getExchangePairs()
              }
              countTimes = countTimes + 1
              print(countTimes)
              if selectCurrencyTableView.isHidden == true {
                  
                  selectCurrencyTableView.isHidden = false
              } else {
                  selectCurrencyTableView.isHidden = true
              }
              firstButton.adjustsImageWhenHighlighted = false
              
              secondButton.adjustsImageWhenHighlighted = false
              DispatchQueue.main.async {
                  self.selectCurrencyTableView.reloadData()
                  
              }
        
    }
    
    
       
       
       func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
           
           
           return myCurrency.count
       }
       func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
           let cell = selectCurrencyTableView.dequeueReusableCell(withIdentifier: "exchangeCurrencyTableViewCell", for: indexPath) as! exchangeCurrencyTableViewCell
           
           
           
           secondButton.isSelected = false
           cell.coinTitle.text = String("\(myCurrency[indexPath.row])")
        cell.coinImage.image = UIImage(named: "\(myCurrencyId[indexPath.row]).png")
           //    cell.coinImage.image = UIImage(named: myCurrency[indexPath.row])
           
           
           
           
           
           return cell
       }
       
       func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
           
           if let cellB = selectCurrencyTableView.cellForRow(at: indexPath) as? exchangeCurrencyTableViewCell {
               
               selectedCryptoSecond = cellB.coinTitle.text
               getExchangeRate(getCryptoId: myCurrencyId[indexPath.row])
             //  firstButton.setTitle(selectedCryptoSecond, for: .normal)
               firstImage.image = UIImage(named: "\(myCurrencyId[indexPath.row]).png")
            firstButton.setTitle(selectedCryptoSecond, for: .normal)
               secondTblClose = true
               selectCurrencyTableView.isHidden = true
               firstButton.isSelected = false
               // exchangeCalculations()
               
           }
           
       }
       
       func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
           
           return 55
       }
    
   
   
    
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let countdots = (exchangeOfFirstTxt.text?.components(separatedBy: ".").count)! - 1
        
        if (exchangeOfFirstTxt.text?.first == ".") {
            return false
            
        }
        if countdots > 0 && string == "."
        {
            return false
        }
        //   return true
        let dotString = "."
        if let text = exchangeOfFirstTxt.text {
            let isDeleteKey = string.isEmpty
            
            if !isDeleteKey {
                if text.contains(dotString) {
                    if text.components(separatedBy: dotString)[1].count == 6 {
                        return false
                    }
                }
                
            }
        }
        let aSet = NSCharacterSet(charactersIn:"0123456789.").inverted
        let compSepByCharInSet = string.components(separatedBy: aSet)
        let numberFiltered = compSepByCharInSet.joined(separator: "")
        return string == numberFiltered
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
    }
    
    override func viewWillDisappear(_ animated: Bool) {
    }
    override func viewWillAppear(_ animated: Bool) {
    }
    
    
    func retrieveValues()  {
        Timer.scheduledTimer(withTimeInterval: 15, repeats: true, block: { _ in
            print("Taimeris strada ")
            DispatchQueue.main.async {
                //self.getExchangeData()
            }
        })
        
        
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool // called when 'return' key pressed. return NO to ignore.
    {
        textField.resignFirstResponder()
        return true;
    }
   
   
  
    @IBAction func onChanged(_ sender: UITextField) {
        if (exchangeOfFirstTxt.text?.first == ".") {
            exchangeOfFirstTxt.text? = "0"
         //   return
            
        }
        var rate = (onChangedRate as! NSString).doubleValue
        if exchangeOfFirstTxt.text == "" || exchangeOfFirstTxt.text!.count < 1 {
            self.sumOfExchange.text = "$0.00"
            return
            
        }
        let userSum = Double(exchangeOfFirstTxt.text!)
        var sumOfAll = userSum! * rate
        self.sumOfExchange.text = String(format: "$%0.4f", sumOfAll)
      //  showExchangeRate(coinRate: selectedCryptoSecond!)
    }
    
    
    
    /// - Opens in tableView available cryptos:
    func getRatesUpdates()  {
        Timer.scheduledTimer(withTimeInterval: 15, repeats: true, block: { _ in
            
            DispatchQueue.main.asyncAfter(deadline: .now() + 15.0, execute: {
               // self.exchangeCalculations()
            })
            
        })
        
    }
    
    
    func getCryptos() {
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        Alamofire.request("https://api.coincap.io/v2/rates/", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    //   print(jsonArray)
                    
                    if let json = jsonArray["data"] as? NSArray {
                        for transcation in json {
                            print(transcation)
                            let symbol = (transcation as AnyObject).value(forKey: "symbol") //transcation["symbol"] as? String
                           
                            let cryptoTitle = (transcation as AnyObject).value(forKey: "name")
                            let getIdOfCrypto = (transcation as AnyObject).value(forKey: "id")
                            //transcation["id"] as? String
                            
                            let type = (transcation as AnyObject).value(forKey: "type")
                            
                            if (type as! String == "crypto") {
                                print(transcation)
                                self.myCurrency.append(symbol as! String)
                                self.myCurrencyId.append(getIdOfCrypto as! String)
                            }
                            
                        }
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    self.getExchangeRate(getCryptoId: "bitcoin")
                }
                
        }
        
    }
    
    // "api.coincap.io/v2/rates/bitcoin"
    func getExchangeRate(getCryptoId : String) {
        print("Funckija tiek izsaukta \(getCryptoId)")
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
   //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/rates/\(getCryptoId)", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    print(jsonArray)

                    if let json = jsonArray["data"] as? NSDictionary {
                        print(json)
                        let ratesUsd = (json as AnyObject).value(forKey: "rateUsd") //transcation["symbol"] as? String
                        let symbol = (json as AnyObject).value(forKey: "symbol") //transcation["symbol"] as? String

                        self.onChangedRate = ratesUsd as! String
                        self.showExchangeRate(coinRate: ratesUsd as! String, symbol: symbol as! String)
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    
                }
                
        }
        
    }
    
    func showExchangeRate(coinRate : String, symbol : String) {
        let getPercents = (coinRate as NSString).doubleValue
        self.exchangeRateLabel.text = String(format: "Exchange rate 1 \(symbol) = %0.4f $", getPercents)
        mainPercents = getPercents
        if exchangeOfFirstTxt.text == "" || exchangeOfFirstTxt.text!.count < 1 {
            self.sumOfExchange.text = "$0.00"
            return

        }
        let userSum = Double(exchangeOfFirstTxt.text!)
        var sumOfAll = userSum! * getPercents
        self.sumOfExchange.text = String(format: "$%0.4f", sumOfAll)
        
    }
 

    @IBAction func dismiss(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
          let goToMain = storyboard.instantiateViewController(withIdentifier: "goHere")
        self.navigationController?.present(goToMain, animated: true, completion: nil)
        
    }
    func dismiss() {
       
    }
    
 
    

    
    func showAlert(_ message: String) {
        let alertController = UIAlertController(title: "CryptoMe", message: message, preferredStyle: UIAlertController.Style.alert)
        alertController.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default,handler: nil))
        self.present(alertController, animated: true, completion: nil)
    }
    
    @IBAction func goToSettings(_ sender: Any) {
           
           
           let backItem = UIBarButtonItem()
           backItem.title = ""
           navigationItem.backBarButtonItem = backItem
           navigationItem.backBarButtonItem?.tintColor = .white
           let toSettings = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "settingsViewController") as! settingsViewController
           self.navigationController?.pushViewController(toSettings, animated: true)
           
       }
  
}
