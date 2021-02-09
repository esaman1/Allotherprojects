//
//  ratesHistoryTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 15/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class ratesHistoryTableViewCell: UITableViewCell {

    @IBOutlet var whatSpendLbl: UILabel!
    @IBOutlet var whatGetLbl: UILabel!
    @IBOutlet var dateLbl: UILabel!
    @IBOutlet var exchangedFromLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
