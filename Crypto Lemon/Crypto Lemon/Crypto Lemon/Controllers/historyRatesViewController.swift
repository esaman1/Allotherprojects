//
//  historyRatesViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 15/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import RealmSwift

class historyRatesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet var ratesTableView: UITableView!
    let realm = try? Realm()
    var ratesRecord: Results<HistoryRatesObject> {
        get {
            return realm!.objects(HistoryRatesObject.self)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let fileUrl = Realm.Configuration.defaultConfiguration.fileURL{
            print("FILE URL IS",fileUrl)
        }
        ratesTableView.delegate = self
        ratesTableView.dataSource = self
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let numOfSections: Int = 0

        let alertsRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "rateID")

        
        if alertsRecords?.count == 0 {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "There is no history rates"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            tableView.backgroundView  = noDataLabel
            tableView.separatorStyle  = .none
        } else {
             let sortRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "rateID")
            return (sortRecords?.count)!
            
        }
        
        
        return numOfSections
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = ratesTableView.dequeueReusableCell(withIdentifier: "ratesHistoryTableViewCell", for: indexPath) as! ratesHistoryTableViewCell
        let formatterForDate = DateFormatter()
        formatterForDate.dateFormat = "dd-MM-LLLL"
        formatterForDate.dateStyle = .medium
        
        let formatterForTime = DateFormatter()
        formatterForTime.dateFormat = "dd-MM"
        formatterForTime.timeStyle = .medium
     /*
        
        let sortRecords = realm?.objects(HistoryRatesObject.self).sorted(byKeyPath: "rateID")
        let getRecords = sortRecords![indexPath.row]
        let formatteddate = formatterForDate.string(from: (getRecords.dateCreated))

        cell.exchangedFromLbl.text = String("Exchanged from \(getRecords.currencyFrom)")
        cell.dateLbl.text = String(formatteddate)
        cell.whatGetLbl.text = String(format: "+ %0.2f%", getRecords.getMoney)
        cell.whatSpendLbl.text = String(format: "- $%0.2f%", getRecords.spendMoney)


        */
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }

}
