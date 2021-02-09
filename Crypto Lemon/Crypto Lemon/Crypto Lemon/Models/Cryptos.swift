//
//  Cryptos.swift
//  cryptMe
//
//  Created by Ints Graveris on 09/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import Foundation
import UIKit


struct Cryptos {
    
    
    let id : String
    let cryptoTitle : String
    let cryptoName : String
    let changePercent24Hr : String
    let cryptoRate : String
    let type : String
  
    
    init(id : String, cryptoTitle: String, cryptoName : String, changePercent24Hr: String, cryptoRate : String, type: String) {
        
        self.id = id
        self.cryptoTitle = cryptoTitle
        self.cryptoName = cryptoName
        self.changePercent24Hr = changePercent24Hr
        self.cryptoRate = cryptoRate
        self.type = type

    }
    
    
    
}
