//
//  addNewCoinTableViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 29/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import Alamofire
import RealmSwift


class addNewCoinTableViewController: UITableViewController {
    
    
    var allCryptos = [Cryptos]()
    
    @IBOutlet var addCoinTableView: UITableView!
    
    
    
    let realm = try? Realm()
    var ratesRecord: Results<HistoryRatesObject> {
        get {
            return realm!.objects(HistoryRatesObject.self)
        }
    }
    
    var realmDbCoin = [String()]
    override func viewDidLoad() {
        super.viewDidLoad()
        if let fileUrl = Realm.Configuration.defaultConfiguration.fileURL{
            print("FILE URL IS",fileUrl)
        }
        navigationItem.title = "Add Favorite Coin"
        // Uncomment the following line to preserve selection between presentations
        addCoinTableView.delegate = self
        addCoinTableView.dataSource = self
       // self.clearsSelectionOnViewWillAppear = false
        
        getCryptos()
      //  getAllRealmRecords()
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let numOfSections: Int = 0
        var counter = 0
        for getRecords in allCryptos {
            counter = counter + 1
        }
        //  let alertsRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "coinID")
        //   let sortRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "coinID")
        print("FROM ROWS", counter)
        return counter
    }
    func getCryptos() {
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        Alamofire.request("https://api.coincap.io/v2/assets", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    //   print(jsonArray)
                    
                    if let json = jsonArray["data"] as? NSArray {
                        // print(json)
                        for transcation in json {
                            var symbol = (transcation as AnyObject).value(forKey: "symbol") //transcation["symbol"] as? String
                            var rateUsd = (transcation as AnyObject).value(forKey: "priceUsd") //transcation["rateUsd"] as? String
                            var id = (transcation as AnyObject).value(forKey: "id") //transcation["id"] as? String
                            var changePercent24Hr = (transcation as AnyObject).value(forKey: "changePercent24Hr") //transcation["id"] as? String
                            var cryptoTitle = (transcation as AnyObject).value(forKey: "name") //transcation["id"] as? String
                            if symbol == nil {
                                symbol = ""
                            }
                            if rateUsd == nil {
                                rateUsd = ""
                            }
                            if id == nil {
                                id = ""
                            }
                            if changePercent24Hr == nil {
                                changePercent24Hr = ""
                            }
                            if cryptoTitle == nil {
                                cryptoTitle = ""
                            }
                            self.allCryptos.append((Cryptos(id: id as? String ?? "", cryptoTitle: cryptoTitle as? String ?? "", cryptoName: symbol as? String ?? "" , changePercent24Hr: changePercent24Hr as? String ?? "", cryptoRate: rateUsd as? String ?? "", type: "crypto")))
                            
                        }
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    self.addCoinTableView.reloadData()
                }
                
        }
        
    }
    
    
    func getAllRealmRecords() {
        
        let sortRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "coinID")
        if sortRecords!.count > 0 {
            for findRecords in sortRecords! {
                print(findRecords.coinTitle)
                print(findRecords.coinFullName)
                realmDbCoin.append(findRecords.coinFullName)
                
            }
        } else {
            print("No records")
        }
       
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = addCoinTableView.dequeueReusableCell(withIdentifier: "addCoinTableViewCell", for: indexPath) as! addCoinTableViewCell
        
        
       let getAllCryptos = allCryptos[indexPath.row]
        
        
  cell.coinLbl.text = getAllCryptos.cryptoTitle
 cell.coinImg.image = UIImage(named: "\(getAllCryptos.id).png")
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let realm = try? Realm()
        var newRate = HistoryRatesObject()
        
        let getAllCryptos = allCryptos[indexPath.row]
        
        newRate = HistoryRatesObject.init(coinID: newRate.incrementID(), coinTitle: getAllCryptos.cryptoName, coinFullName: getAllCryptos.cryptoTitle, coinCreated: Date())
        
        print(realmDbCoin)
        print(getAllCryptos.cryptoName)
        if realmDbCoin.contains(getAllCryptos.cryptoTitle) == true {
            showToast(message: "You're already have this crypto")
            print(realmDbCoin)
            print(getAllCryptos.cryptoName)
            return
        }
        
        try! realm?.write {
            realm?.add(newRate)
        }
        
        
        
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .white
        let toHistory = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "watchListViewController") as! watchListViewController
        self.navigationItem.hidesBackButton = true
        //  self.navigationController?.popToRootViewController(animated: true)
        self.navigationController?.pushViewController(toHistory, animated: true)
        
        
    }
    
    /*
     // Override to support conditional editing of the table view.
     override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the specified item to be editable.
     return true
     }
     */
    
    /*
     // Override to support editing the table view.
     override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
     if editingStyle == .delete {
     // Delete the row from the data source
     tableView.deleteRows(at: [indexPath], with: .fade)
     } else if editingStyle == .insert {
     // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
     }
     }
     */
    
    /*
     // Override to support rearranging the table view.
     override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
     
     }
     */
    
    /*
     // Override to support conditional rearranging of the table view.
     override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the item to be re-orderable.
     return true
     }
     */
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}
extension addNewCoinTableViewController {
    
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
