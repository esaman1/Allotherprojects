//
//  watchListViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 25/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import Charts
import RealmSwift
import Alamofire

class watchListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UICollectionViewDelegate, UICollectionViewDataSource {
   

    @IBOutlet var watchListCollectionView: UICollectionView!
    @IBOutlet var lineChartView: LineChartView!
    @IBOutlet var watchListTableView: UITableView!
    
    
    @IBOutlet var priceChangesLbl: UILabel!
    @IBOutlet var priceUsdLbl: UILabel!
    
    let realm = try? Realm()
    var ratesRecord: Results<HistoryRatesObject> {
        get {
            return realm!.objects(HistoryRatesObject.self)
        }
    }
    
    var allCryptos = [Cryptos]()
    var realmDbCoin = [String()]
    var ratesValuesOneMinute = [Double]()
    var ratesValuesString = [String]()

    
    var selectedIdToShowAllInfo : String = ""
    var getCryptoTitle : String = ""
    var getLowValue : Double = 0.00
    var getMaxValue : Double = 0.00
    var getLastValue : Double = 0.00
    
    var marketCapUsdFor : Double = 0.00
    var supplyFor : Double = 0.00
    var changePercent24HrFor = 0.00
    var priceUsdFor = 0.00
    var volumeUsd24HrFor = 0.00
    var titleArray = ["Market Cap", "24H Volume", "Circulating Supply", "24H High", "24H Low", "Open"]
    override func viewDidLoad() {
        super.viewDidLoad()
        getAllRealmRecords()

        watchListTableView.delegate = self
        watchListTableView.dataSource = self
        
        
        
        watchListCollectionView.delegate = self
        watchListCollectionView.dataSource = self
        // Do any additional setup after loading the view.
        
        getChartDataOneMinute()
        
    }
    func getAllRealmRecords() {
        
        let sortRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "coinID")
        for findRecords in sortRecords! {
            print(findRecords.coinTitle)
            print(findRecords.coinFullName)
            realmDbCoin.append(findRecords.coinFullName)
            
        }
        
        getCryptos()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return titleArray.count
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = watchListTableView.dequeueReusableCell(withIdentifier: "watchListTableViewCell") as! watchListTableViewCell
        cell.selectionStyle = .none
        
        
        cell.mainLbl.text = titleArray[indexPath.row]
        if indexPath.row == 0 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", marketCapUsdFor)
        } else if indexPath.row == 1 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", volumeUsd24HrFor)
        } else if indexPath.row == 2 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", supplyFor)
        } else if indexPath.row == 3 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", getMaxValue)
        } else if indexPath.row == 4 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", getLowValue)
        } else if indexPath.row == 5 {
            cell.dynamicLbl.text = String(format: "$%0.2f%", getLastValue)
        }
        
        
        return cell

    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let numOfSections: Int = 0
        var counter = 0
        for getRecords in allCryptos {
            counter = counter + 1
        }
        if counter == 0 {
            self.watchListCollectionView.setEmptyMessage("You don't have favourite cryptocurrencies")
        } else {
            self.watchListCollectionView.restore()
        }
        print("FROM ROWS", counter)
        return counter
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = watchListCollectionView.dequeueReusableCell(withReuseIdentifier: "watchListCollectionViewCell", for: indexPath) as! watchListCollectionViewCell
        
        let showRecords = allCryptos[indexPath.row]

        
        cell.watchListLbl.text = showRecords.cryptoTitle
        cell.watchListImg.image = UIImage(named: showRecords.id)
     
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let showRecords = allCryptos[indexPath.item]
        
        selectedIdToShowAllInfo = showRecords.id
       // self.allCryptos.removeAll()

        getCryptos()
        getMinMaxNowValue()
        getCryptoAssetts()
    }
    
    
    @IBAction func addNewCoin(_ sender: UIButton) {
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .white
        let toHistory = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "addNewCoinTableViewController") as! addNewCoinTableViewController
      //  self.navigationItem.hidesBackButton = true
        //  self.navigationController?.popToRootViewController(animated: true)
        self.navigationController?.pushViewController(toHistory, animated: true)
        //addNewCoinTableViewController
    }
    
    func getCryptos() {
       //
        allCryptos.removeAll()
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
                            
                            if self.realmDbCoin.contains(cryptoTitle as? String ?? "") == true {
                                self.allCryptos.append((Cryptos(id: id as? String ?? "", cryptoTitle: cryptoTitle as? String ?? "", cryptoName: symbol as? String ?? "" , changePercent24Hr: changePercent24Hr as? String ?? "", cryptoRate: rateUsd as? String ?? "", type: "crypto")))
                            }
                            
                            
                        }
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                   
                    print(self.allCryptos.count)
                    self.getCryptoAssetts()
               //     self.getChartDataOneMinute()
                    self.getMinMaxNowValue()

                    self.watchListTableView.reloadData()
                    self.watchListCollectionView.reloadData()

                }
                
        }
        
    }
    
    
    
    
    private func updateGraph(){
        
        var line2ChartEntry  = [ChartDataEntry]()
        line2ChartEntry.removeAll()
        self.lineChartView.gridBackgroundColor = NSUIColor.white
        self.lineChartView.rightAxis.enabled = false
        self.lineChartView.rightAxis.drawAxisLineEnabled = false
        
        //   self.chtChart.xAxis.axisMinimum = 0.0
        self.lineChartView.xAxis.drawGridLinesEnabled = false
        self.lineChartView.xAxis.axisLineColor = UIColor.clear
        self.lineChartView.xAxis.labelFont = UIFont(name: "AvenirNext-Regular", size: 0.1)!
        self.lineChartView.xAxis.labelTextColor = UIColor(red: 23/255, green: 27/255, blue: 33/255, alpha: 1.0)
        //
        self.lineChartView.leftAxis.enabled = false
        self.lineChartView.rightAxis.enabled = false
        self.lineChartView.leftAxis.drawAxisLineEnabled = false
        
        self.lineChartView.backgroundColor = #colorLiteral(red: 0.09215500206, green: 0.1058230326, blue: 0.1306458712, alpha: 1) //#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1)
        self.lineChartView.translatesAutoresizingMaskIntoConstraints = false
        self.lineChartView.legend.enabled = false
        self.lineChartView.chartDescription?.enabled = false
        //  self.chtChart.dragEnabled = true
        self.lineChartView.setScaleEnabled(true)
        self.lineChartView.pinchZoomEnabled = true
        self.lineChartView.maxVisibleCount = 0
        //  self.lineChartView.lineData.
        
        
      
            for i in 0..<ratesValuesOneMinute.count {
                
                let value = ChartDataEntry(x: Double(i), y: Double(ratesValuesOneMinute[i])) // here we set the X and Y status in a data chart entry
                line2ChartEntry.append(value) // here we add it to the data set
            }
        
        
        
        
        let line2 = LineChartDataSet(entries: line2ChartEntry, label: "Graph data") //Here we convert lineChartEntry to a LineChartDataSet
        line2.colors = [NSUIColor.orange]
        
        // ----------
        // intervals d1, d2 ,d3, d4
        let data = LineChartData() //This is the object that will be added to the chart
        data.addDataSet(line2)
        data.setDrawValues(false)
        
        line2.colors = [NSUIColor(red: 232/255, green: 252/255, blue: 88/255, alpha: 1.0)] //Sets the colour to blue
        line2.drawCirclesEnabled = false
        line2.lineWidth = 2
        line2.drawValuesEnabled = false
        line2.fillColor = #colorLiteral(red: 0.9111591578, green: 0.9894618392, blue: 0.3278787136, alpha: 1)
        line2.drawFilledEnabled = true
        
        lineChartView.data = data //finally - it adds the chart data to the chart and causes an update
        
        // ---------
        lineChartView.animate(xAxisDuration: 1.38)
        lineChartView.isUserInteractionEnabled = false
    }
    
    func getMinMaxNowValue() {
        var result: [PointEntry] = []
        
        if selectedIdToShowAllInfo.count < 1 {
            selectedIdToShowAllInfo = "bitcoin"
        }
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/\(selectedIdToShowAllInfo)/history?interval=m1&limit=100", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesOneMinute = Array(doubleArray.suffix(100))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                  self.getLowValue = self.ratesValuesOneMinute.min()!
                self.getMaxValue = self.ratesValuesOneMinute.max()!
                self.getLastValue = self.ratesValuesOneMinute.last!
               // self.updateGraph()
                    
                self.watchListTableView.reloadData()
                    // varbut lai sanemt high and low value
                    
                }
                
        }
        
    }
    
    
    func getChartDataOneMinute() {
        var result: [PointEntry] = []
        
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/bitcoin/history?interval=m1&limit=100", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesOneMinute = Array(doubleArray.suffix(100))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                  self.getLowValue = self.ratesValuesOneMinute.min()!
                self.getMaxValue = self.ratesValuesOneMinute.max()!
                self.getLastValue = self.ratesValuesOneMinute.last!
                self.updateGraph()
                    
                  //  self.watchListTableView.reloadData()
                    // varbut lai sanemt high and low value
                    
                }
                
        }
        
    }
    
     func getCryptoAssetts() {
        if getCryptoTitle.count < 2 {
            if (allCryptos.count) == 0 {
                return
            } else {
                getCryptoTitle = (allCryptos.first?.id)!

            }
        } else {
            getCryptoTitle = selectedIdToShowAllInfo
        }
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle)", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [String:Any] {
                        print(json)
                        
                        //for transcation in json {
                        let marketCapUsd = (json as AnyObject).value(forKey: "marketCapUsd")
                        let volumeUsd24Hr = (json as AnyObject).value(forKey: "volumeUsd24Hr")
                        let priceUsd = (json as AnyObject).value(forKey: "priceUsd")
                        let changePercent24Hr = (json as AnyObject).value(forKey: "changePercent24Hr")
                        let supply = (json as AnyObject).value(forKey: "supply")
                        
                        
                        //  self.marketCapUsdFor = marketCapUsd
                        var marketCapUsdShow = (marketCapUsd as! NSString).doubleValue
                        var volumeUsd24HrShow = (volumeUsd24Hr as! NSString).doubleValue
                        var priceUsdShow = (priceUsd as! NSString).doubleValue
                        var changePercent24HrShow = (changePercent24Hr as! NSString).doubleValue
                        var supplyShow = (supply as! NSString).doubleValue
                        
                        
                        self.marketCapUsdFor = marketCapUsdShow
                        self.volumeUsd24HrFor = volumeUsd24HrShow
                        self.priceUsdFor = priceUsdShow
                        self.changePercent24HrFor = changePercent24HrShow
                        self.supplyFor = supplyShow
                        self.priceUsdLbl.text = String(format: "$%0.2f%", priceUsdShow)
                        // self.getMaxValue =
                        
                        
                        if changePercent24HrShow > 0 {
                            //   self.cryptoChangeImage.image = UIImage(named: "up.png")
                            
                            self.priceChangesLbl.text = String(format: "+ %0.2f%%", changePercent24HrShow)
                            
                            
                        } else {
                            
                            //    self.cryptoChangeImage.image = UIImage(named: "down.png")
                            self.priceChangesLbl.text = String(format: "%0.2f%%", changePercent24HrShow)
                            
                        }
                        
                        
                        
                        
                        DispatchQueue.main.async {
                            //  self.setProperties()
                            self.watchListTableView.reloadData()
                            
                        }
                        // }
                    }
                    
                    
                    
                }
                
                
        }
        
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
extension UICollectionView {

    func setEmptyMessage(_ message: String) {
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height))
        messageLabel.text = message
        messageLabel.textColor = .black
        messageLabel.numberOfLines = 0;
        messageLabel.textAlignment = .center;
        messageLabel.font = UIFont(name: "TrebuchetMS", size: 15)
        messageLabel.sizeToFit()

        self.backgroundView = messageLabel;
    }

    func restore() {
        self.backgroundView = nil
    }
}
