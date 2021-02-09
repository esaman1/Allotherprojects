//
//  dashboardTableViewCell.swift
//  cryptoBlackout
//
//  Created by Ints Graveris on 16/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class dashboardTableViewCell: UITableViewCell {
    
    
    @IBOutlet var dashboardPercentChanges: UILabel!
    @IBOutlet var dashboardPrice: UILabel!
    @IBOutlet var dashboardTitle: UILabel!
    @IBOutlet var dashboardFullName: UILabel!
    @IBOutlet var dashboardImg: UIImageView!
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
