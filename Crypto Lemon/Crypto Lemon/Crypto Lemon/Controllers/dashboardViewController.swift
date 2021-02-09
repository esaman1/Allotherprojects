//
//  dashboardViewController.swift
//  cryptoBlackout
//
//  Created by Ints Graveris on 16/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import Foundation
import UIKit
//import Charts
import SwiftKeychainWrapper
import Alamofire



class dashboardViewController: UIViewController, UISearchResultsUpdating, UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func updateSearchResults(for searchController: UISearchController) {
        //
    }
    
    
    
    @IBOutlet var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    
    
    @IBOutlet var popularCollectionView: UICollectionView!
    var marketChanges : Double = 0.0
    
    var exchangeCurrencies = ["BTC", "ETH", "LTC", "DEX", "DASH"]
    
    private let refreshControl = UIRefreshControl()
    
    var selectedValues = Set<String>()   // Unique values only for cryptoCollectionView
    
    var specialBalanceDEX : Double = 0
    
    var indexForCount = 0
    // var lineChartView = LineChartView()
    // var lineChartEntry = [ChartDataEntry]()
    var expandedRows = Set<Int>()
    // var socket:SocketIOClient!
    
    var activityIndicator : UIActivityIndicatorView = UIActivityIndicatorView()
    
    var message = [String: String]()
    
    
    var percentSign = "%"
    var ifReceived : String?
    
    
    // var searchController: UISearchController!
    var allCryptos = [Cryptos]()
    var filteredAllCryptos = [Cryptos]()
    var isConnected: Bool = false
    var searchedCurrency : [String]!
    var searching = false
    
    var userCurrency : String? = KeychainWrapper.standard.string(forKey: "mainCurrency")
    var userCurrencySymbol : String? = KeychainWrapper.standard.string(forKey: "mainCurrencySymbol")
    
    
    var timerReloadData : Timer?
    var allBalance : Double = 0
    
    
    var ratesBitcoinString = [String]()
    var ratesEthereumString = [String]()
    var ratesLitecoinString = [String]()

    
    var getBitcoinChart = [Double]()
    var getEthereumChart = [Double]()
    var getLitecoinChart = [Double]()

    
    
    var popularCryptos = [Cryptos]()  // max 5
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if userCurrency == nil {
            userCurrency = "usd"
            userCurrencySymbol = "$"
        }
        searchBar.delegate = self
        
        getCryptos()
        // retrieveValues()// TIMEOUT 15 seconds reloading data
        navigationItem.hidesBackButton = true
        
        tableView.delegate = self
        tableView.dataSource = self
        
        popularCollectionView.delegate = self
        popularCollectionView.dataSource = self
        tableView.tableFooterView = UIView() // Removes
        self.tableView.rowHeight = UITableView.automaticDimension
        self.tableView.estimatedRowHeight = 10.0  // 2.0
        
        refreshControl.addTarget(self, action: #selector(doSomething), for: .valueChanged)
        refreshControl.attributedTitle = NSAttributedString(string: "Pull to refresh")
        refreshControl.tintColor = .white
        
        
        
        indexForCount = 1
        
        // accountBalance.addSubview(activityIndicator)
        activityIndicator.startAnimating()
        
        
        self.navigationItem.title = "Stock"
        let textAttributes = [NSAttributedString.Key.foregroundColor:UIColor.white]
        navigationController?.navigationBar.titleTextAttributes = textAttributes
        
        self.tabBarController?.tabBar.isHidden = false
        
        //  searchedCurrency = allCryptos.filter { $0.cryptoTitle.contains({ searchBar.text }) }
        
        // Initializing with searchResultsController set to nil means that
        
        getBitcoin()
        getEthereum()
        getLitecoin()
        
    }
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
     //   navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
      //  navigationController?.setNavigationBarHidden(false, animated: animated)
        timerReloadData?.invalidate()
        
    }
    
    @objc func doSomething(refreshControl: UIRefreshControl) {
        
        // update crypto values
        DispatchQueue.main.async {
            // self.getDEX()
            
            
        }
        refreshControl.endRefreshing()
    }
    
    func getBitcoin() {
            var result: [PointEntry] = []
            let headers = ["Content-Type": "application/json",
                           "Accept": "application/json"]
            //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
            Alamofire.request("https://api.coincap.io/v2/assets/bitcoin/history?interval=m1&limit=100", headers: headers) //replace url with your url
                .responseJSON { response in
                    if let jsonArray = response.result.value as? AnyObject {
                        if let json = jsonArray["data"] as? [[String:Any]] {
                            for transcation in json {
                                if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                    self.ratesBitcoinString.append(ratesUsd as! String)
                                    let doubleArray = self.ratesBitcoinString.compactMap(Double.init)
                                    self.getBitcoinChart = Array(doubleArray.suffix(100))
                 
                                }
                            }
                      
                        }
       
                    }
                    DispatchQueue.main.async {
                        print(self.getBitcoinChart.count)
                        self.popularCollectionView.reloadData()
                       
                    }
            }
    }
    
    func getEthereum() {
        var result: [PointEntry] = []
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/ethereum/history?interval=m1&limit=100", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                self.ratesEthereumString.append(ratesUsd as! String)
                                let doubleArray = self.ratesEthereumString.compactMap(Double.init)
                                self.getEthereumChart = Array(doubleArray.suffix(100))
                                
                            }
                        }
                        
                    }
                    
                }
                DispatchQueue.main.async {
                    self.popularCollectionView.reloadData()
                    
                }
        }
    }
    
    func getLitecoin() {
        var result: [PointEntry] = []
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/litecoin/history?interval=m1&limit=100", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                self.ratesLitecoinString.append(ratesUsd as! String)
                                let doubleArray = self.ratesLitecoinString.compactMap(Double.init)
                                self.getLitecoinChart = Array(doubleArray.suffix(100))
                                
                            }
                        }
                        
                    }
                    
                }
                
                DispatchQueue.main.async {
                    self.popularCollectionView.reloadData()
                    
                }
        }
    }
    
    
    func getCryptos() {
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        Alamofire.request("https://api.coincap.io/v2/assets", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    //   print(jsonArray)
                    
                    if let json = jsonArray["data"] as? NSArray {
                        print(json)
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
                            
                            if (id as? String ?? "" == "bitcoin" || id as? String ?? "" == "litecoin" || id as? String ?? "" == "ethereum") {
                                self.popularCryptos.append((Cryptos(id: id as? String ?? "", cryptoTitle: cryptoTitle as? String ?? "", cryptoName: symbol as? String ?? "" , changePercent24Hr: changePercent24Hr as? String ?? "", cryptoRate: rateUsd as? String ?? "", type: "crypto")))
                            }
                            
                            self.allCryptos.append((Cryptos(id: id as? String ?? "", cryptoTitle: cryptoTitle as? String ?? "", cryptoName: symbol as? String ?? "" , changePercent24Hr: changePercent24Hr as? String ?? "", cryptoRate: rateUsd as? String ?? "", type: "crypto")))
                            
                        }
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    self.tableView.reloadData()
                    self.popularCollectionView.reloadData()
                }
                
        }
        
    }
    
    
    
}

extension dashboardViewController : UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var counter = 0
        var filterCounter = 0
        var returnValue = 0
        
        
        if searching == true {
            for countRecords in filteredAllCryptos {
                //  if countRecords.type == "crypto" {
                filterCounter = filterCounter + 1
                
                // }
                
            }
            return filterCounter
        } else {
            for countRecords in allCryptos {
                //  if countRecords.type == "crypto" {
                counter = counter + 1
                
                // }
                
            }
            
            return counter
        }
        
        
        return returnValue
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 88
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let accessToken: String? = KeychainWrapper.standard.string(forKey: "accessToken")
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        let cell = tableView.dequeueReusableCell(withIdentifier: "dashboardTableViewCell", for: indexPath) as! dashboardTableViewCell
        
        if #available(iOS 10.0, *) {
            tableView.refreshControl = refreshControl
        } else {
            tableView.addSubview(refreshControl)
        }
        
        
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if searching == true {
            let getAllCryptos = filteredAllCryptos[indexPath.row]
            
            let transferToDouble = (getAllCryptos.cryptoRate as NSString).doubleValue
            let getPercents = (getAllCryptos.changePercent24Hr as NSString).doubleValue
            
            
            if getPercents < 0 {
                cell.dashboardPercentChanges.textColor = .red
                cell.dashboardPercentChanges.text = String(format: "%0.2f%%", getPercents)
            } else {
                cell.dashboardPercentChanges.textColor = #colorLiteral(red: 0.3294117647, green: 0.6823529412, blue: 0, alpha: 1)
                cell.dashboardPercentChanges.text = String(format: "+ %0.2f%%", getPercents)
            }
            
            cell.dashboardTitle.text = getAllCryptos.cryptoName
            cell.dashboardPrice.text = String(format: "$%0.2f%", transferToDouble)
            cell.dashboardFullName.text = getAllCryptos.cryptoTitle
            cell.dashboardImg.image = UIImage(named: "\(getAllCryptos.id).png")
            
        } else {
            let getAllCryptos = allCryptos[indexPath.row]
            
            let transferToDouble = (getAllCryptos.cryptoRate as NSString).doubleValue
            let getPercents = (getAllCryptos.changePercent24Hr as NSString).doubleValue
            
            
            if getPercents < 0 {
                cell.dashboardPercentChanges.textColor = .red
                cell.dashboardPercentChanges.text = String(format: "%0.2f%%", getPercents)
            } else {
                cell.dashboardPercentChanges.textColor = #colorLiteral(red: 0.3294117647, green: 0.6823529412, blue: 0, alpha: 1)
                cell.dashboardPercentChanges.text = String(format: "+ %0.2f%%", getPercents)
            }
            
            cell.dashboardTitle.text = getAllCryptos.cryptoName
            cell.dashboardPrice.text = String(format: "$%0.2f%", transferToDouble)
            cell.dashboardFullName.text = getAllCryptos.cryptoTitle
            cell.dashboardImg.image = UIImage(named: "\(getAllCryptos.id).png")
        }
        
        
        
        
        return cell
        
    }
    
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .white
        let toCryptoWallet = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "cryptoDetailViewController") as! cryptoDetailViewController
        
        if (searching == true) {
            let getCryptosIn = filteredAllCryptos[indexPath.row]
            
            toCryptoWallet.getCryptoTitle = getCryptosIn.id
            
            toCryptoWallet.cryptoFullName = getCryptosIn.cryptoTitle
        } else {
            let getCryptosIn = allCryptos[indexPath.row]
            
            toCryptoWallet.getCryptoTitle = getCryptosIn.id
            
            toCryptoWallet.cryptoFullName = getCryptosIn.cryptoTitle
        }
         
        
        self.navigationController?.pushViewController(toCryptoWallet, animated: true)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        var counter = 0
        for countRecords in popularCryptos {
            //  if countRecords.type == "crypto" {
            counter = counter + 1
            
            // }
            
        }
        return counter
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = popularCollectionView.dequeueReusableCell(withReuseIdentifier: "dashboardCollectionViewCell", for: indexPath) as! dashboardCollectionViewCell
        
        let getAllCryptos = popularCryptos[indexPath.row]

        let transferToDouble = (getAllCryptos.cryptoRate as NSString).doubleValue

        cell.coinNameLbl.text = getAllCryptos.cryptoName
        cell.coinPriceLbl.text = String(format: "$%0.2f%", transferToDouble)
        cell.xValues = ["0", "2", "3", "4", "5","0", "2", "3", "4", "5","0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5","0", "2", "3", "4", "5","0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5", "4", "5", "0", "2", "3", "4", "5", "0", "2", "3", "4", "5"]
        print("we counted", cell.xValues.count)

        
        if getAllCryptos.cryptoName == "BTC" {
            
            
            if getBitcoinChart.count < 2 {
                print("GAIDAM")
            } else {
                cell.yValues = self.getBitcoinChart
                cell.setChart(dataPoints: cell.xValues, values: cell.yValues)
                
            }
            
            
                
            
        } else if getAllCryptos.cryptoName == "ETH" {
            
    
            if getEthereumChart.count < 2 {
                print("GAIDAM")
            } else {
                
                cell.yValues = self.getEthereumChart
                cell.setChart(dataPoints: cell.xValues, values: cell.yValues)
            }
            
                        
                        
    
            
        } else if getAllCryptos.cryptoName == "LTC" {
           
                        
            if getLitecoinChart.count < 2 {
                print("GAIDAM")
            } else {
                cell.yValues = self.getLitecoinChart
                cell.setChart(dataPoints: cell.xValues, values: cell.yValues)
                
            }
            
            
        }
        
        
        
      //  cell.categoryLbl.layer.borderWidth = 1.0
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
      
        
        print("WHEASLJDKALSHDASKHDKLASHDKAJSGDKJASHDASD")
    }
    
    
    func showAlert(_ message: String) {
        let alertController = UIAlertController(title: "Crypto Blackout", message: message, preferredStyle: UIAlertController.Style.alert)
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
    
    
} // -- END

extension dashboardViewController: UISearchBarDelegate {
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        print(searchText)
        var searchString = searchText.trimWhiteSpace()
        if searchString.count > 0 {
            searching = true
            filteredAllCryptos = allCryptos.filter {
                $0.cryptoTitle.localizedCaseInsensitiveContains(searchText)
            }
            
            
            print(filteredAllCryptos)
            tableView.reloadData()
        } else {
            searching = false
            tableView.reloadData()
        }
    }
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searching = false
        searchBar.text = ""
        tableView.reloadData()
    }
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchBar.showsCancelButton = true
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchBar.text = nil
        searchBar.showsCancelButton = false
        searchBar.text = ""
        searching = false
        tableView.reloadData()
        // Remove focus from the search bar.
        searchBar.endEditing(true)
        
        // Perform any necessary work.  E.g., repopulating a table view
        // if the search bar performs filtering.
    }
    
    
}

extension String {
    func trimWhiteSpace() -> String {
        let string = self.trimmingCharacters(in: .whitespacesAndNewlines)
        return string
    }
}
