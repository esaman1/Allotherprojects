//
//  watchListTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 25/09/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class watchListTableViewCell: UITableViewCell {

    @IBOutlet var dynamicLbl: UILabel!
    @IBOutlet var mainLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
