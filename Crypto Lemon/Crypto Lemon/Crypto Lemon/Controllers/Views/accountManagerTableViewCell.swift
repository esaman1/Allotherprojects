//
//  accountManagerTableViewCell.swift
//  andITSolutionsProject
//
//  Created by Ints Graveris on 21.06.18.
//  Copyright © 2018. g. Ints Graveris. All rights reserved.
//

import UIKit
//import Charts
//import RealmSwift

protocol accountDashboard {
    
    
    func tapToWallet(_ sender: UIButton)
}

class ExpandingTableViewCellContent {
    var currencyFullName: String?
    var currencyTitle: String?
    var currentValueToUSD: String?
    var ratesChanges: String?
    var marketValue: String?
    var selectedAccountBalance: String?
    
    
    var lowestValue: String?
    var highestValue: String?
    
    var expanded: Bool
    
    init(currencyFullName: String, currencyTitle: String, currentValueToUSD: String, ratesChanges: String, marketValue: String, selectedAccountBalance: String, lowestValue: String, highestValue: String) {
        self.currencyFullName = currencyFullName
        self.currencyTitle = currencyTitle
        self.currentValueToUSD = currentValueToUSD
        self.ratesChanges = ratesChanges
        self.marketValue = marketValue
        self.selectedAccountBalance = selectedAccountBalance
        self.lowestValue = lowestValue
        self.highestValue = highestValue
        
        self.expanded = false    // bija false
    }
}

class accountManagerTableViewCell: UITableViewCell {
  //  @IBOutlet weak var chtChart: LineChartView!
    @IBOutlet weak var stackView: UIStackView!
    @IBOutlet weak var currentValueToUSD: UILabel!
    @IBOutlet weak var selectedAccountBalance: UILabel!
    @IBOutlet weak var highestValue: UILabel!
    @IBOutlet weak var lowestValue: UILabel!
    @IBOutlet weak var ratesChanges: UILabel!
    @IBOutlet weak var marketValue: UILabel!
    @IBOutlet weak var currencyTitle: UIButton!
    @IBOutlet weak var currencyFullName: UILabel!
    @IBOutlet weak var imageOfCurrency: UIImageView!
    
    @IBOutlet weak var imgHeightConstraint: NSLayoutConstraint!
    
    
    var delegate: accountDashboard!
    var yValues  =  [Double]()
    var xValues = [String]()
    var indexPath: IndexPath!
    override func awakeFromNib() {
        super.awakeFromNib()
        
        /*
        self.chtChart.gridBackgroundColor = NSUIColor.white
        self.chtChart.rightAxis.enabled = false
        self.chtChart.rightAxis.drawAxisLineEnabled = false
        
        //   self.chtChart.xAxis.axisMinimum = 0.0
        self.chtChart.xAxis.drawGridLinesEnabled = false
        self.chtChart.xAxis.axisLineColor = UIColor.clear
        self.chtChart.xAxis.labelFont = UIFont(name: "AvenirNext-Regular", size: 1.0)!
        self.chtChart.xAxis.labelTextColor = UIColor(red: 20/255, green: 34/255, blue: 60/255, alpha: 1.0)
        //
        self.chtChart.leftAxis.enabled = false
        self.chtChart.rightAxis.enabled = false
        self.chtChart.leftAxis.drawAxisLineEnabled = false
        
        self.chtChart.backgroundColor = #colorLiteral(red: 0.3578933477, green: 0.3579024673, blue: 0.3578975797, alpha: 1)
        self.chtChart.translatesAutoresizingMaskIntoConstraints = false
        self.chtChart.legend.enabled = false
        
        
        self.chtChart.chartDescription?.enabled = false
        //  self.chtChart.dragEnabled = true
        self.chtChart.setScaleEnabled(true)
        self.chtChart.pinchZoomEnabled = true
        self.chtChart.maxVisibleCount = 0
        */
    }
    
    var isExpanded:Bool = false
    {
        didSet
        {
            if !isExpanded {
                self.imgHeightConstraint.constant = 0.0
                
            } else {
                self.imgHeightConstraint.constant = 117
            }
        }
    }
    
    func set(content: ExpandingTableViewCellContent) {
        
        self.currencyFullName.text = content.currencyFullName
        self.currencyTitle.titleLabel?.text = content.currencyTitle
        self.currentValueToUSD.text = content.currentValueToUSD
        self.selectedAccountBalance.text = content.selectedAccountBalance
        self.ratesChanges.text = content.ratesChanges
        self.marketValue.text = content.marketValue
        
        self.lowestValue.text = content.expanded ? content.lowestValue : ""
        self.highestValue.text = content.expanded ? content.highestValue : ""
        
    }
    
  /*  func setChart(dataPoints: [String], values: [Double]) {
        chtChart.noDataText = "You need to provide data for the chart."
        
        var dataEntries: [ChartDataEntry] = []
        
        for i in 0..<dataPoints.count {
            let dataEntry = ChartDataEntry(x: Double(i) + 0.5, y: values[i])
            dataEntries.append(dataEntry)
        }
        
        let chartDataSet = LineChartDataSet(entries: dataEntries, label: "")
        let chartData = LineChartData(dataSets: [chartDataSet])
        
        
        /*    let point = gesture.location(in: chart)
         let highlight = chart.getHighlightByTouchPoint(point)
         chart.highlightValue(highlight)   */
        
        chartDataSet.colors = [NSUIColor(red: 238/255, green: 234/255, blue: 74/255, alpha: 1.0)] //Sets the colour to blue
        chartDataSet.drawCirclesEnabled = false
        chartDataSet.lineWidth = 2
        chartDataSet.drawValuesEnabled = false
        
        chartDataSet.fillColor = #colorLiteral(red: 0.9333333333, green: 0.9176470588, blue: 0.2901960784, alpha: 1)
        chartDataSet.drawFilledEnabled = true
        
        chtChart.data = chartData
        
    }
    func chartValueSelected(chartView: ChartViewBase, entry: ChartDataEntry, dataSetIndex: Int, highlight: Highlight) {
        print("Seit ir value \(entry.x)")
    }
    */
    @IBAction func tapToWallet(_ sender: UIButton) {
        
        
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
