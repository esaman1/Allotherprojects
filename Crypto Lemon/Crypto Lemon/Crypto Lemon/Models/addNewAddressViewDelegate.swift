//
//  addNewAddressViewDelegate.swift
//  cryptMe
//
//  Created by Ints Graveris on 30/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import Foundation
import Alamofire
import SwiftKeychainWrapper

protocol addNewAddressViewDelegate: class {
    
    func saveBtnTapped(type: String, address: String)
    
    func cancelButtonTapped()
}

