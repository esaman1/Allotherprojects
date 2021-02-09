//
//  settingsTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 01/10/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class settingsTableViewCell: UITableViewCell {

    @IBOutlet var imgView: UIImageView!
    @IBOutlet var dynamicLbl: UILabel!
    @IBOutlet var titleLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
