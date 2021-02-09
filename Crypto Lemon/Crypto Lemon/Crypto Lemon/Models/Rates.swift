//
//  Rates.swift
//  cryptMe
//
//  Created by Ints Graveris on 16/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//


import Foundation
import RealmSwift
import Realm
import UIKit



class HistoryRatesObject: Object {
    
    @objc dynamic var coinID : Int = 0
    @objc dynamic var coinTitle = ""
    @objc dynamic var coinFullName = ""
    @objc dynamic var coinCreated = Date()
    
    override static func primaryKey() -> String? {
        
        return "coinID"
        
    }
    
    convenience init(coinID: Int, coinTitle: String, coinFullName: String, coinCreated: Date) {
        
        
        
        self.init()
        self.coinID = coinID
        self.coinTitle = coinTitle
        self.coinFullName = coinFullName
        self.coinCreated = coinCreated
        
        
    }
    
    func incrementID() -> Int {
        let realm = try! Realm()
        return (realm.objects(HistoryRatesObject.self).max(ofProperty: "coinID") as Int? ?? 0) + 1
    }
    
}
