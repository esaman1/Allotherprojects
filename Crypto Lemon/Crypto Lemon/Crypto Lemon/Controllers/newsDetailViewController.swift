//
//  newsDetailViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 16/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import SDWebImage


class newsDetailViewController: UIViewController {

    @IBOutlet var detailNews: UITextView!
    
    @IBOutlet var newsImage: UIImageView!
    var imageUrl : String?
    var newsUrl : String?
    var descriptionAll: String?
    override func viewDidLoad() {
        super.viewDidLoad()

        
        detailNews.text = descriptionAll
        newsImage.sd_setImage(
            with: URL(string: imageUrl!),placeholderImage: UIImage(named: "cryptMe_icon.png"),options: SDWebImageOptions(rawValue: 0), completed: { image, error, cacheType, imageURL in
                // your rest code
        })
        // Do any additional setup after loading the view.
    }
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
       // navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
      //  navigationController?.setNavigationBarHidden(false, animated: animated)
        
    }
    @IBAction func continueToSite(_ sender: UIButton) {
        
        let alert = UIAlertController(title: "Continue to site?", message: "Reading full article in site", preferredStyle: UIAlertController.Style.alert)
        
        // Logout button
        let OKAction = UIAlertAction(title: "Yes, continue", style: UIAlertAction.Style.default) { (action:UIAlertAction!) in
            // SyncUser.current?.logOut()
            if let url = URL(string: "\(self.newsUrl!)") {
                UIApplication.shared.open(url)
            }
            
        }
        alert.addAction(OKAction)
        
        // Cancel button
        let cancelAction = UIAlertAction(title: "No, stay", style: .cancel) { (action:UIAlertAction!) in
            print("Cancel button tapped");
        }
        alert.addAction(cancelAction)
        
        // Present Dialog message
        present(alert, animated: true, completion:nil)
        
        
    }
    
    
    
}
