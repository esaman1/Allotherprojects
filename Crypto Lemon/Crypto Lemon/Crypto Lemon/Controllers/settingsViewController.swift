//
//  settingsViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 01/10/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit

class settingsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    

    @IBOutlet var settingsTblView: UITableView!
    
    
    var arrayTitle = ["Currency", "Language", "Share App", "Rate this app"]
     var arrayDynamic = ["US Dollar (USD)", "English", "Share via social networks", "Rate on AppStore"]
    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.title = "Settings"
        settingsTblView.delegate = self
        settingsTblView.dataSource = self
        // Do any additional setup after loading the view.
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayTitle.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = settingsTblView.dequeueReusableCell(withIdentifier: "settingsTableViewCell", for: indexPath) as! settingsTableViewCell
        
        cell.selectionStyle = .none
        cell.titleLbl.text = arrayTitle[indexPath.row]
        cell.dynamicLbl.text = arrayDynamic[indexPath.row]
        if (indexPath.row == 0) {
            cell.imgView.image = UIImage(named: "icons8-currency-settings-48")

            
        } else if (indexPath.row == 1) {
            cell.imgView.image = UIImage(named: "icons8-language-60")

            
        } else if (indexPath.row == 2) {
            cell.imgView.image = UIImage(named: "icons8-connect-48")

            
        } else if (indexPath.row == 3) {
            cell.imgView.image = UIImage(named: "icons8-rating-48")

            
        }
        
        
        return cell
        
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
   
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
    func openUrl(urlStr:String!) {
           
           if let url = NSURL(string:urlStr) {
               UIApplication.shared.open(url as URL, options: [:], completionHandler: nil)
           }
    
       }
    
    func rateApp() {
        guard let url = URL(string: "itms-apps://itunes.apple.com/app/" + "appId") else {
            return
        }
        if #available(iOS 10, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)

        } else {
            UIApplication.shared.openURL(url)
        }
    }

}
