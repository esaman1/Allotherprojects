//
//  alertsTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 12/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class alertsTableViewCell: UITableViewCell {

    @IBOutlet var priceCoin: UILabel!
    @IBOutlet var titleCoin: UILabel!
    @IBOutlet var imageCoin: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
