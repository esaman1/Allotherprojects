//
//  newsViewController.swift
//  cryptMe
//
//  Created by Ints Graveris on 11/08/2019.
//  Copyright © 2019 iGrow. All rights reserved.
//

import UIKit
import Alamofire
import SDWebImage

class newsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
   
    // TOKEN FOR NEWS
    // 66c623349e9e3b8f64d742420b4f7f83da999113
    @IBOutlet var newsTableView: UITableView!
   
    var allNews = [News]()
    var categoryNews = "all"
    override func viewDidLoad() {
        super.viewDidLoad()

        
        getNews()
        
        newsTableView.delegate = self
        newsTableView.dataSource = self
        // Do any additional setup after loading the view.
    }


    func getNews() {
        self.allNews.removeAll()
        let headers = ["Content-Type": "application/json",
                       "Accept": "application/json",
                       "Authorization": "05d89775f13d916177ef26489e04c26f363c19263b4227ae8b64c7e69e9fcf18"]
        //     curl --location --request GET "api.coincap.io/v2/rates/bitcoin"
        Alamofire.request("https://min-api.cryptocompare.com/data/v2/news/?lang=EN", headers: headers) //replace url with your url
            .responseJSON { response in
                if let jsonArray = response.result.value as? AnyObject {
                    
                    
                    if let json = jsonArray["Data"] as? NSArray {
                        print(json)

                        for transcation in json {
                            print(transcation)
                            // categories
                            // imageurl
                            // body (in detail)
                            // published_on
                            // url (in detail)
                            // title
                            let title = (transcation as AnyObject).value(forKey: "title") as? String
                            let url = (transcation as AnyObject).value(forKey: "url") as? String
                            let published_on = (transcation as AnyObject).value(forKey: "published_on") as? Int
                            let body = (transcation as AnyObject).value(forKey: "body") as? String
                            let imageurl = (transcation as AnyObject).value(forKey: "imageurl") as? String
                            let categories = (transcation as AnyObject).value(forKey: "categories") as? String
                            let timeInterval = Double(published_on!)
                            
                            // create NSDate from Double (NSTimeInterval)
                            let myNSDate = Date(timeIntervalSince1970: timeInterval)
                           // let stingDate = String(myNSDate)
                            let formatterForDate = DateFormatter()
                            formatterForDate.dateFormat = "dd-MM"
                            formatterForDate.dateStyle = .medium
                            let formatteddate = formatterForDate.string(from: myNSDate)
                            if self.categoryNews == "all" {
                                 self.allNews.append(News(title: title!, url: url!, published_on: formatteddate, body: body!, imageurl: imageurl!, categories: categories!))
                            } else if self.categoryNews == "btc" && categories == "BTC" {
                                self.allNews.append(News(title: title!, url: url!, published_on: formatteddate, body: body!, imageurl: imageurl!, categories: categories!))
                            }  else if self.categoryNews == "eth" && categories == "ETH" {
                                self.allNews.append(News(title: title!, url: url!, published_on: formatteddate, body: body!, imageurl: imageurl!, categories: categories!))
                            }
                           

                        }
                    }
                    
                    
                    
                }
                DispatchQueue.main.async {
                    self.newsTableView.reloadData()
                }
                
        }
        
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
      //  var returnValue = 0
        var counter = 0
        for countNews in allNews {
            if self.categoryNews == "all" {
                counter = counter + 1

            } else if self.categoryNews == "btc" && countNews.categories == "BTC" {
                counter = counter + 1

            }  else if self.categoryNews == "eth" && countNews.categories  == "ETH" {
                counter = counter + 1

            }
        }
        return counter
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
         let cell = newsTableView.dequeueReusableCell(withIdentifier: "newsTableViewCell", for: indexPath) as! newsTableViewCell
        
        let getNews = allNews[indexPath.row]
        cell.descriptionLbl.text = getNews.title
        cell.categoryDateLbl.text = String("\(getNews.categories) • \(getNews.published_on)")
        cell.imageNews.sd_setImage(
            with: URL(string: getNews.imageurl),placeholderImage: UIImage(named: "cryptoLemon_ICON.png"),options: SDWebImageOptions(rawValue: 0), completed: { image, error, cacheType, imageURL in
                // your rest code
        })
    //    if categoryNews == "all" {
        /*
            cell.descriptionLbl.text = getNews.title
            cell.categoryDateLbl.text = String("\(getNews.categories) • \(getNews.published_on)")
            cell.imageNews.sd_setImage(
                with: URL(string: getNews.imageurl),placeholderImage: UIImage(named: "cryptMe_icon.png"),options: SDWebImageOptions(rawValue: 0), completed: { image, error, cacheType, imageURL in
                    // your rest code
            })
            
        }
        else if getNews.categories.contains("BTC") && categoryNews == "btc" {
            cell.descriptionLbl.text = getNews.title
            cell.categoryDateLbl.text = String("\(getNews.categories) • \(getNews.published_on)")
            cell.imageNews.sd_setImage(
                with: URL(string: getNews.imageurl),placeholderImage: UIImage(named: "cryptMe_icon.png"),options: SDWebImageOptions(rawValue: 0), completed: { image, error, cacheType, imageURL in
                    // your rest code
            })
            
        } else if getNews.categories.contains("ETH") && categoryNews == "eth" {
            cell.descriptionLbl.text = getNews.title
            cell.categoryDateLbl.text = String("\(getNews.categories) • \(getNews.published_on)")
            cell.imageNews.sd_setImage(
                with: URL(string: getNews.imageurl),placeholderImage: UIImage(named: "cryptMe_icon.png"),options: SDWebImageOptions(rawValue: 0), completed: { image, error, cacheType, imageURL in
                    // your rest code
            })
            
        }
 
 */
      
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 110
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let backItem = UIBarButtonItem()
        backItem.title = ""
        navigationItem.backBarButtonItem = backItem
        navigationItem.backBarButtonItem?.tintColor = .black
        let toDEtails = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "newsDetailViewController") as! newsDetailViewController
        
        
        let getNews = allNews[indexPath.row]
        
       
            toDEtails.imageUrl = getNews.imageurl
            toDEtails.descriptionAll = getNews.body
            toDEtails.newsUrl = getNews.url
            self.navigationController?.pushViewController(toDEtails, animated: true)

   


     
        
    }
}
