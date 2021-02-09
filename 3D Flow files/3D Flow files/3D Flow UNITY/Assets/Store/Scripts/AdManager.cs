using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using GoogleMobileAds.Api;

public class AdManager : MonoBehaviour
{

    public bool testMode = false;


    public string androidAppID;
    public string androidBannerID;
    public string androidInterstitialID;
    public string androidRewardID;

    public string IOSAppID;
    public string IOSBannerID;
    public string IOSInterstitialID;
    public string IOSRewardID;


    private string androidTestBanner = "ca-app-pub-3940256099942544/6300978111";
    private string androidTestInterstitial = "ca-app-pub-3940256099942544/1033173712";
    private string androidTestReward = "ca-app-pub-3940256099942544/5224354917";

    private string IOSTestBanner = "ca-app-pub-3940256099942544/2934735716";
    private string IOSTestInterstitial = "ca-app-pub-3940256099942544/4411468910";
    private string IOSTestReward = "ca-app-pub-3940256099942544/1712485313";

    [SerializeField]
    private int playAdAfter = 3;







    public static AdManager instance;

    public int adCounter;
    private InterstitialAd interstitial;
    private BannerView bannerView;
    private RewardBasedVideoAd rewardBasedVideo;

    public GameObject rewardScreen;

    // Start is called before the first frame update
    void Start()
    {
        instance = this;

        DontDestroyOnLoad(this.gameObject);




#if UNITY_ANDROID
                    string appId = androidAppID;
#elif UNITY_IPHONE
        string appId = IOSAppID;
#else
                    tring appId = "unexpected_platform";
#endif

        // Initialize the Google Mobile Ads SDK.
        MobileAds.Initialize(appId);


        //Request Interstitial
        RequestInterstitial();

        //Request Banner
        this.RequestBanner();


        //Request Rewarded Video
        // Get singleton reward based video ad reference.
        this.rewardBasedVideo = RewardBasedVideoAd.Instance;

        // Called when the user should be rewarded for watching a video.
        rewardBasedVideo.OnAdRewarded += HandleRewardBasedVideoRewarded;

        this.RequestRewardBasedVideo();


    }



    private void RequestBanner()
    {
        string BanneradUnitId = "";
#if UNITY_ANDROID
                if (testMode)
                {
                    BanneradUnitId= androidTestBanner;
                }
                else
                {
                    BanneradUnitId = androidBannerID;
                }
#elif UNITY_IPHONE
        if (testMode)
        {
            BanneradUnitId = IOSTestBanner;
        }
        else
        {
            BanneradUnitId = IOSBannerID;
        }
#else
                    BanneradUnitId = "unexpected_platform";
#endif

        // Create a 320x50 banner at the top of the screen.
        bannerView = new BannerView(BanneradUnitId, AdSize.SmartBanner, AdPosition.Bottom);

        // Create an empty ad request.
        AdRequest request = new AdRequest.Builder().Build();

        // Load the banner with the request.
        bannerView.LoadAd(request);
    }
 



    private void RequestInterstitial()
    {
        string InterstitialadUnitId = "";
#if UNITY_ANDROID
                  if (testMode)
                {
                    InterstitialadUnitId = androidTestInterstitial;
                }
                else
                {
                    InterstitialadUnitId = androidInterstitialID;
                }
#elif UNITY_IPHONE
        if (testMode)
        {
            InterstitialadUnitId = IOSTestInterstitial;
        }
        else
        {
            InterstitialadUnitId = IOSInterstitialID;
        }
#else
                InterstitialadUnitId = "unexpected_platform";
#endif

        // Initialize an InterstitialAd.
        this.interstitial = new InterstitialAd(InterstitialadUnitId);
        // Create an empty ad request.
        AdRequest request = new AdRequest.Builder().Build();
        // Load the interstitial with the request.
        this.interstitial.LoadAd(request);
    }





    private void RequestRewardBasedVideo()
    {
        string RewardadUnitId = "";
#if UNITY_ANDROID
        if (testMode)
        {
            RewardadUnitId = androidTestReward;
        }
        else
        {
            RewardadUnitId = androidRewardID;
        }
#elif UNITY_IPHONE
        if (testMode)
        {
            RewardadUnitId = IOSTestReward;
        }
        else
        {
            RewardadUnitId = IOSRewardID;
        }
#else
            string adUnitId = "unexpected_platform";
#endif

        // Create an empty ad request.
        AdRequest request = new AdRequest.Builder().Build();
        // Load the rewarded video ad with the request.
        this.rewardBasedVideo.LoadAd(request, RewardadUnitId);
    }












    public void GameOver()
    {
        int gamesPlayed = PlayerPrefs.GetInt("game_count");
        gamesPlayed++;
        PlayerPrefs.SetInt("game_count", gamesPlayed);

        //Debug.Log("games played:" + gamesPlayed);





        if (StoreScripts.Score.instance.GetPoint() > 0)
        {
         


                if (rewardBasedVideo.IsLoaded())
                {

                    ShowRewardScreen();

                }
                else
                {
                    this.RequestRewardBasedVideo();
                }
            
        }





        StoreScripts.CanvasManager.instance.StartAddingScoreToCoins();

        // if (gamesPlayed > 4)
        // {

            PlayAd();
        //}
    }






    public void PlayAd()
    {
        adCounter = LoadAdCounter();

        adCounter++;
        if (!this.interstitial.IsLoaded())
        {

            RequestInterstitial();
        }

        if (adCounter >= playAdAfter)
        {


            if (this.interstitial.IsLoaded())
            {
                this.interstitial.Show();
                print("show ad");
                adCounter = 0;
            }

        }

        SaveAds(adCounter);

    }

    public int LoadAdCounter()
    {
        return PlayerPrefs.GetInt("AdsKey");
    }

    private void SaveAds(int value)
    {
        PlayerPrefs.SetInt("AdsKey", value);
    }






    public void ShowRewardScreen(){

        rewardScreen.SetActive(true);

        RewardUI.instance.ShowReward();
    }



    public void HandleRewardBasedVideoRewarded(object sender, Reward args)
    {
        string type = args.Type;
        double amount = args.Amount;
        print("User rewarded with: " + amount.ToString() + " " + type);

        //RewardUI.instance.StartDiamondAnimation();
        StoreScripts.CanvasManager.instance.StartAddingScoreToCoins();

        this.RequestRewardBasedVideo();
    }


    public void UserOptToWatchAd()
    {
        if (rewardBasedVideo.IsLoaded())
        {
            rewardBasedVideo.Show();
        }
    }








}
