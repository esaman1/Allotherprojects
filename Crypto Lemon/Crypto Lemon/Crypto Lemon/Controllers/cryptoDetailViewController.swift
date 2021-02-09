//
//  cryptoDetailViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 09/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//


import UIKit
import Charts
import Alamofire


class cryptoDetailViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    
    @IBOutlet var percentChangesLbl: UILabel!
    @IBOutlet var cryptoImageView: UIImageView!
    @IBOutlet var cryptoFullNameLbl: UILabel!
    @IBOutlet var fullPriceLbl: UILabel!
    @IBOutlet var detailTableView: UITableView!
    @IBOutlet var lineChartView: LineChartView!
    
    
    
    @IBOutlet var twelveHours: UIButton!
    @IBOutlet var dayBtn: UIButton!
    @IBOutlet var hourBtn: UIButton!
    @IBOutlet var minBtn: UIButton!
    
    
    var titleArray = ["Market Cap", "24H Volume", "Circulating Supply", "24H High", "24H Low", "Open"]
    var getCryptoTitle : String?
    var getLowValue : Double = 0.00
    var getMaxValue : Double = 0.00
    var getLastValue : Double = 0.00
    
    var marketCapUsdFor : Double = 0.00
    var supplyFor : Double = 0.00
    var changePercent24HrFor = 0.00
    var priceUsdFor = 0.00
    var volumeUsd24HrFor = 0.00
    
    var intervalType = "1m"
    var cryptoName :String?
    var cryptoFullName : String?
    
    
    var ratesValuesOneMinute = [Double]()
    var ratesValuesFiveMinute = [Double]()
    var ratesValuesOneHour = [Double]()
    var ratesValuesOneDay = [Double]()
    var ratesValuesString = [String]()
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print("Result", getCryptoTitle)
        getCryptoAssetts()
        cryptoImageView.image = UIImage(named: "\(getCryptoTitle!).png")
        cryptoFullNameLbl.text = cryptoFullName!
        
        getChartDataOneMinute()
        navigationItem.title = "Crypto details"
        detailTableView.delegate = self
        detailTableView.dataSource = self
        // Do any additional setup after loading the view.
    }
    
    func getChartDataOneMinute() {
        var result: [PointEntry] = []
        
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle!)/history?interval=m1", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesOneMinute = Array(doubleArray.suffix(500))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    print("Count value", self.ratesValuesOneMinute.count)
                    self.getLowValue = self.ratesValuesOneMinute.min()!
                    self.getMaxValue = self.ratesValuesOneMinute.max()!
                    self.getLastValue = self.ratesValuesOneMinute.last!
                    //    self.cryptoLowLbl.text = String(format: "$%0.2f%", self.getLowValue!)
                    //      self.cryptoHighLbl.text = String(format: "$%0.2f%", self.getMaxValue!)
                    //       self.cryptoOpenLbl.text = String(format: "$%0.2f%", self.getLastValue!)
                    self.updateGraph()
                    
                    self.detailTableView.reloadData()
                    
                }
                
        }
        
    }
    
    func getChartDataFiveMinute() {
        var result: [PointEntry] = []
        
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle!)/history?interval=h1", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesFiveMinute = Array(doubleArray.suffix(500))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    self.updateGraph()
                    
                    self.getLowValue = self.ratesValuesFiveMinute.min()!
                    self.getMaxValue = self.ratesValuesFiveMinute.max()!
                    self.getLastValue = self.ratesValuesFiveMinute.last!
                    if (self.getLowValue == nil || self.getMaxValue == nil || self.getLastValue == nil) {
                        
                        
                    } else {
                        
                        self.updateGraph()
                        self.detailTableView.reloadData()
                        
                    }
                }
                
        }
        
    }
    func getChartDataHour() {
        var result: [PointEntry] = []
        
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle!)/history?interval=d1", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesOneHour = Array(doubleArray.suffix(500))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    
                    self.updateGraph()
                    self.detailTableView.reloadData()
                    
                    
                }
                
        }
        
    }
    func getChartDataDay() {
        var result: [PointEntry] = []
        
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle!)/history?interval=h12&limit=500", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    if let json = jsonArray["data"] as? [[String:Any]] {
                        for transcation in json {
                            // print(transcation)
                            
                            if let ratesUsd = (transcation as AnyObject).value(forKey: "priceUsd") {
                                
                                self.ratesValuesString.append(ratesUsd as! String)
                                let doubleArray = self.ratesValuesString.compactMap(Double.init)
                                
                                self.ratesValuesOneDay = Array(doubleArray.suffix(500))
                                
                                // self.ratesValues.append(ratesUsd as! Double)
                            }
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    
                    self.updateGraph()
                    self.detailTableView.reloadData()
                    
                    
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
        
        self.lineChartView.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1) //#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1)
        self.lineChartView.translatesAutoresizingMaskIntoConstraints = false
        self.lineChartView.legend.enabled = false
        self.lineChartView.chartDescription?.enabled = false
        //  self.chtChart.dragEnabled = true
        self.lineChartView.setScaleEnabled(true)
        self.lineChartView.pinchZoomEnabled = true
        self.lineChartView.maxVisibleCount = 0
        //  self.lineChartView.lineData.
        
        
        if intervalType == "1m" {
            for i in 0..<ratesValuesOneMinute.count {
                
                let value = ChartDataEntry(x: Double(i), y: Double(ratesValuesOneMinute[i])) // here we set the X and Y status in a data chart entry
                line2ChartEntry.append(value) // here we add it to the data set
            }
        } else if intervalType == "1h" {
            for i in 0..<ratesValuesFiveMinute.count {
                
                let value = ChartDataEntry(x: Double(i), y: Double(ratesValuesFiveMinute[i])) // here we set the X and Y status in a data chart entry
                line2ChartEntry.append(value) // here we add it to the data set
            }
        } else if intervalType == "1d" {
            for i in 0..<ratesValuesOneHour.count  {
                
                let value = ChartDataEntry(x: Double(i), y: Double(ratesValuesOneHour[i])) // here we set the X and Y status in a data chart entry
                line2ChartEntry.append(value) // here we add it to the data set
            }
        } else if intervalType == "1w" {
            for i in 0..<ratesValuesOneDay.count {
                
                let value = ChartDataEntry(x: Double(i), y: Double(ratesValuesOneDay[i])) // here we set the X and Y status in a data chart entry
                line2ChartEntry.append(value) // here we add it to the data set
            }
        }
        
        
        
        let line2 = LineChartDataSet(entries: line2ChartEntry, label: "Graph data") //Here we convert lineChartEntry to a LineChartDataSet
        line2.colors = [NSUIColor.orange]
        
        // ----------
        // intervals d1, d2 ,d3, d4
        let data = LineChartData() //This is the object that will be added to the chart
        data.addDataSet(line2)
        data.setDrawValues(false)
        
        line2.colors = [NSUIColor(red: 232/255, green: 252/255, blue: 84/255, alpha: 1.0)] //Sets the colour to blue
        line2.drawCirclesEnabled = false
        line2.lineWidth = 2
        line2.drawValuesEnabled = false
        line2.fillColor = #colorLiteral(red: 0.9098039216, green: 0.9882352941, blue: 0.3294117647, alpha: 1)
        line2.drawFilledEnabled = true
        
        lineChartView.data = data //finally - it adds the chart data to the chart and causes an update
        
        // ---------
        lineChartView.animate(xAxisDuration: 0.9)
        lineChartView.isUserInteractionEnabled = false
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return titleArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "watchListTableViewCell", for: indexPath) as! watchListTableViewCell
        
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
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 55
    }
    func getCryptoAssetts() {
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json"]
        Alamofire.request("https://api.coincap.io/v2/assets/\(getCryptoTitle!)", headers: headers) //replace url with your url
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
                        self.fullPriceLbl.text = String(format: "$%0.2f%", priceUsdShow)
                        // self.getMaxValue =
                        
                        
                        if changePercent24HrShow > 0 {
                            //   self.cryptoChangeImage.image = UIImage(named: "up.png")
                            self.percentChangesLbl.textColor = #colorLiteral(red: 0.9098039216, green: 0.9882352941, blue: 0.3294117647, alpha: 1)
                            self.percentChangesLbl.text = String(format: "+ %0.2f%%", changePercent24HrShow)
                            
                            
                        } else {
                            
                            //    self.cryptoChangeImage.image = UIImage(named: "down.png")
                            self.percentChangesLbl.text = String(format: "%0.2f%%", changePercent24HrShow)
                            
                        }
                        
                        
                        
                        
                        DispatchQueue.main.async {
                            //  self.setProperties()
                            self.detailTableView.reloadData()
                            
                        }
                        // }
                    }
                    
                    
                    
                }
                
                
        }
        
    }
    
    @IBAction func intervalChanges(_ sender: UIButton) {
        
        let filterCheck = sender.tag
        switch filterCheck {
        case 0:
            intervalType = "1m"
            minBtn.backgroundColor = #colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1)
            minBtn.setTitleColor(#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1), for: .normal)
            self.getLowValue = self.ratesValuesOneMinute.min()!
            self.getMaxValue = self.ratesValuesOneMinute.max()!
            self.getLastValue = self.ratesValuesOneMinute.last!
            
            
            
            updateGraph()
            self.detailTableView.reloadData()
            
            twelveHours.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            twelveHours.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            hourBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            hourBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            dayBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            dayBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            break
        case 1:
            intervalType = "1h"
            hourBtn.backgroundColor = #colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1)
            hourBtn.setTitleColor(#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1), for: .normal)
            minBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            minBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            twelveHours.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            twelveHours.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            dayBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            dayBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            
            
            if self.ratesValuesFiveMinute.count < 2 {
                getChartDataFiveMinute()
                return
            } else {
                self.getLowValue = self.ratesValuesFiveMinute.min()!
                self.getMaxValue = self.ratesValuesFiveMinute.max()!
                self.getLastValue = self.ratesValuesFiveMinute.last!
                
                
                updateGraph()
                self.detailTableView.reloadData()
                
            }
            
            
            break
        case 2:
            intervalType = "1d"
            twelveHours.backgroundColor = #colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1)
            twelveHours.setTitleColor(#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1), for: .normal)
            minBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            minBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            dayBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            dayBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            hourBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            hourBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            
            if self.ratesValuesOneHour.count < 2 {
                getChartDataHour()
                return
            } else {
                self.getLowValue = self.ratesValuesOneHour.min()!
                self.getMaxValue = self.ratesValuesOneHour.max()!
                self.getLastValue = self.ratesValuesOneHour.last!
                
                updateGraph()
                self.detailTableView.reloadData()
            }
            
            
            break
        case 3:
            intervalType = "1w"
            dayBtn.backgroundColor = #colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1)
            dayBtn.setTitleColor(#colorLiteral(red: 0.9529411765, green: 0.9529411765, blue: 0.9529411765, alpha: 1), for: .normal)
            minBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            minBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            twelveHours.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            twelveHours.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            hourBtn.backgroundColor = #colorLiteral(red: 0.09019607843, green: 0.1058823529, blue: 0.1294117647, alpha: 1)
            hourBtn.setTitleColor(#colorLiteral(red: 0.3333333433, green: 0.3333333433, blue: 0.3333333433, alpha: 1), for: .normal)
            
            if self.ratesValuesOneDay.count < 2 {
                getChartDataDay()
                return
            } else {
                self.getLowValue = self.ratesValuesOneDay.min()!
                self.getMaxValue = self.ratesValuesOneDay.max()!
                self.getLastValue = self.ratesValuesOneDay.last!
                
                updateGraph()
                self.detailTableView.reloadData()
                
            }
            
            
            break
        default:
            // sender.isSelected = false
            // sender.isHighlighted = false
            break
        }
        updateGraph()
        
    }
    
    
}
