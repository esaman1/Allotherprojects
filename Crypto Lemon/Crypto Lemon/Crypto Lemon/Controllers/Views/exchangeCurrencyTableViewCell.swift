//
//  exchangeCurrencyTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 11/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class exchangeCurrencyTableViewCell: UITableViewCell {

    @IBOutlet var coinTitle: UILabel!
    @IBOutlet var coinImage: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
