//
//  newsTableViewCell.swift
//  cryptMe
//
//  Created by Ints Graveris on 11/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class newsTableViewCell: UITableViewCell {

    @IBOutlet var imageNews: UIImageView!
    @IBOutlet var categoryDateLbl: UILabel!
    @IBOutlet var descriptionLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
