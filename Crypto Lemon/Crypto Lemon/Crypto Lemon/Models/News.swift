//
//  News.swift
//  cryptMe
//
//  Created by Ints Graveris on 11/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//


import Foundation
import UIKit


struct News {
    
    
 //  let id : String
    let title : String
    let url : String
    let published_on : String
    let body : String  // description
    let imageurl : String
    let categories : String
    
    
    
    init(title: String, url : String, published_on: String, body : String, imageurl: String, categories: String) {
        
        self.title = title
        self.url = url
        self.published_on = published_on
        self.body = body
        self.imageurl = imageurl
        self.categories = categories

        
    }
    
    
    
}
