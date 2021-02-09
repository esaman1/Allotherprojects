//
//  priceAlertsViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 12/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import Foundation
import UIKit
//import RealmSwift

class priceAlertsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    @IBOutlet weak var alertsTableView: UITableView!
   
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
       
        alertsTableView.delegate = self
        alertsTableView.dataSource = self

    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = alertsTableView.dequeueReusableCell(withIdentifier: "alertsTableViewCell", for: indexPath) as! alertsTableViewCell
       // cell.
        
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
    
    /*
    let realm = try? Realm()
    var alertRecord: Results<AlertsObject> {
        get {
            return realm!.objects(AlertsObject.self)
        }
    }
    
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func createPriceAlert(_ sender: Any) {
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .white
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let nextVC = storyboard.instantiateViewController(withIdentifier: "priceAlertChooseCurrency") as! priceAlertChooseCurrency
        self.navigationController?.pushViewController(nextVC, animated: true)
        
    }
    
    @IBAction func reload(_ sender: MXSegmentedControl) {
        //   alertsTableView.reloadData()
        
    }
    @IBAction func changeCrypto(_ sender: Any) {
        
        if selectionOfRates.selectedIndex == 0 {
            
            imageOfCrypto.image = #imageLiteral(resourceName: "bitcoin")
            nameOfCrypto.text = "Bitcoin"
            currencyOfCrypto.text = "€5434,54"
            
        } else if selectionOfRates.selectedIndex == 1 {
            
            imageOfCrypto.image = #imageLiteral(resourceName: "litecoin")
            nameOfCrypto.text = "Litecoin"
            currencyOfCrypto.text = "€64,33"
            
        } else if selectionOfRates.selectedIndex == 2 {
            
            imageOfCrypto.image = #imageLiteral(resourceName: "ethereum")
            nameOfCrypto.text = "Ethereum"
            currencyOfCrypto.text = "€372,01"
            
            
        }else if selectionOfRates.selectedIndex == 3 {
            
            imageOfCrypto.image = #imageLiteral(resourceName: "DASH")
            nameOfCrypto.text = "DASH"
            currencyOfCrypto.text = "€188,33"
            
            
        }
        alertsTableView.reloadData()
        
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let numOfSections: Int = 0
        
        
        let alertsRecords = realm?.objects(AlertsObject.self).sorted(byKeyPath: "alertID")
        if alertsRecords?.count == 0 {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "There is no alerts"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            tableView.backgroundView  = noDataLabel
            tableView.separatorStyle  = .none
        } else {
            if selectionOfRates.selectedIndex == 0 {
                let BTCaccount = realm?.objects(AlertsObject.self).filter("currencyName = %@", "BTC")
                if BTCaccount?.count == 0 {
                    
                    return 0
                } else {
                    return (BTCaccount?.count)!
                }
                
            } else if selectionOfRates.selectedIndex == 1 {
                let LTCaccount = realm?.objects(AlertsObject.self).filter("currencyName = %@", "LTC")
                if LTCaccount?.count == 0 {
                    return 0
                } else {
                    return (LTCaccount?.count)!
                }
                
                
            } else if selectionOfRates.selectedIndex == 2 {
                let ETHaccount = realm?.objects(AlertsObject.self).filter("currencyName = %@", "ETH")
                if ETHaccount?.count == 0 {
                    return 0
                } else {
                    return (ETHaccount?.count)!
                }
                
            } else if selectionOfRates.selectedIndex == 3 {
                let DASHaccount = realm?.objects(AlertsObject.self).filter("currencyName = %@", "DASH")
                if DASHaccount?.count == nil {
                    return 0
                } else {
                    return (DASHaccount?.count)!
                }
                
            }
        }
        
        return numOfSections
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let formatterForDate = DateFormatter()
        formatterForDate.dateFormat = "dd-MM-LLLL"
        formatterForDate.dateStyle = .medium
        
        let formatterForTime = DateFormatter()
        formatterForTime.dateFormat = "dd-MM"
        formatterForTime.timeStyle = .medium
        
        let cell = alertsTableView.dequeueReusableCell(withIdentifier: "AlertsTableViewCell", for: indexPath) as! AlertsTableViewCell
        
        
        
        if selectionOfRates.selectedIndex == 0 {
            let btcSorting = realm?.objects(AlertsObject.self).filter("currencyName = %@", "BTC")
            let btcRecords = btcSorting![indexPath.row]
            if indexPath.row < (btcSorting?.count)! {
                
                let formatteddate = formatterForDate.string(from: (btcRecords.dateCreated))
                
                cell.currencyBelowAboveLabel.text = String("\(btcRecords.currencyName) is above €\(btcRecords.exchangeRate)")
                cell.dateOfAlertCreated.text = String(formatteddate)
                cell.imageOfCurrency.image = #imageLiteral(resourceName: "bitcoin")
                
            } else {
                print ("nothin")
            }
            
        } else if selectionOfRates.selectedIndex == 1 {
            let ltcSorting = realm?.objects(AlertsObject.self).filter("currencyName = %@", "LTC")
            let ltcRecords = ltcSorting![indexPath.row]
            
            if indexPath.row < (ltcSorting?.count)! {
                
                let formatteddate = formatterForDate.string(from: (ltcRecords.dateCreated))
                
                cell.currencyBelowAboveLabel.text = String("\(ltcRecords.currencyName) is above €\(ltcRecords.exchangeRate)")
                cell.dateOfAlertCreated.text = String(formatteddate)
                cell.imageOfCurrency.image = #imageLiteral(resourceName: "LTC")
                
            } else {
                
                print("nothing")
            }
            
        } else if selectionOfRates.selectedIndex == 2 {
            let ethSorting = realm?.objects(AlertsObject.self).filter("currencyName = %@", "ETH")
            let ethRecords = ethSorting![indexPath.row]
            if indexPath.row < (ethSorting?.count)! {
                
                let formatteddate = formatterForDate.string(from: (ethRecords.dateCreated))
                
                cell.currencyBelowAboveLabel.text = String("\(ethRecords.currencyName) is above €\(ethRecords.exchangeRate)")
                cell.dateOfAlertCreated.text = String(formatteddate)
                cell.imageOfCurrency.image = #imageLiteral(resourceName: "ETH")
                
            } else {
                
                print("not")
            }
        } else if selectionOfRates.selectedIndex == 3 {
            let dashSorting = realm?.objects(AlertsObject.self).filter("currencyName = %@", "DASH")
            let dashRecords = dashSorting![indexPath.row]
            if indexPath.row < (dashSorting?.count)! {
                
                let formatteddate = formatterForDate.string(from: (dashRecords.dateCreated))
                
                cell.currencyBelowAboveLabel.text = String("\(dashRecords.currencyName) is above €\(dashRecords.exchangeRate)")
                cell.dateOfAlertCreated.text = String(formatteddate)
                cell.imageOfCurrency.image = #imageLiteral(resourceName: "DASH")
                
            } else {
                
                print("notthin")
            }
        }
        
        return cell
    }
    
   */
}


