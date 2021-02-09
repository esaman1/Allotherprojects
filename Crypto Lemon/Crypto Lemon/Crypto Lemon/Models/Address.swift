//
//  Address.swift
//  cryptMe
//
//  Created by Ints Graveris on 30/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//


import Foundation
import RealmSwift
import Realm
import UIKit



class Address: Object {
    
    @objc dynamic var addressID : Int = 0
    @objc dynamic var addressType = ""
    @objc dynamic var addressName = ""
    
    override static func primaryKey() -> String? {
        
        return "addressID"
        
    }
    
    convenience init(addressID: Int, addressType: String, addressName: String) {
        
        
        
        self.init()
        self.addressID = addressID
        self.addressType = addressType
        self.addressName = addressName
        
        
    }
    
    func incrementID() -> Int {
        let realm = try! Realm()
        return (realm.objects(Address.self).max(ofProperty: "addressID") as Int? ?? 0) + 1
    }
    
}

