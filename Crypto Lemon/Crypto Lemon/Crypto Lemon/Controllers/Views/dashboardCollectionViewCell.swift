//
//  dashboardCollectionViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 25/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import Charts

class dashboardCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet var coinChartView: LineChartView!
    @IBOutlet var coinPriceLbl: UILabel!
    @IBOutlet var coinNameLbl: UILabel!
    
    
    
 //   var delegate: accountDashboard!
    var yValues  =  [Double]()
    var xValues = [String]()
    var indexPath: IndexPath!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        
        self.coinChartView.gridBackgroundColor = NSUIColor.white
        self.coinChartView.rightAxis.enabled = false
        self.coinChartView.rightAxis.drawAxisLineEnabled = false
        
        //   self.chtChart.xAxis.axisMinimum = 0.0
        self.coinChartView.xAxis.drawGridLinesEnabled = false
        self.coinChartView.xAxis.axisLineColor = UIColor.clear
        self.coinChartView.xAxis.labelFont = UIFont(name: "AvenirNext-Regular", size: 1.0)!
        self.coinChartView.xAxis.labelTextColor = UIColor(red: 20/255, green: 34/255, blue: 60/255, alpha: 1.0)
        //
        self.coinChartView.leftAxis.enabled = false
        self.coinChartView.rightAxis.enabled = false
        self.coinChartView.leftAxis.drawAxisLineEnabled = false
        
        self.coinChartView.backgroundColor = #colorLiteral(red: 0.2017621696, green: 0.2116571069, blue: 0.2529165447, alpha: 1)
        self.coinChartView.translatesAutoresizingMaskIntoConstraints = false
        self.coinChartView.legend.enabled = false
        
        
        self.coinChartView.chartDescription?.enabled = false
        //  self.chtChart.dragEnabled = true
        self.coinChartView.setScaleEnabled(true)
        self.coinChartView.pinchZoomEnabled = true
        self.coinChartView.maxVisibleCount = 0
        
    }
    
    func setChart(dataPoints: [String], values: [Double]) {
        coinChartView.noDataText = "You need to provide data for the chart."
        
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
        
        coinChartView.data = chartData
        
    }
}
