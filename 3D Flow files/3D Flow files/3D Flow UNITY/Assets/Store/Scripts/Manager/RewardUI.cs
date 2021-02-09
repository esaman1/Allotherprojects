using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RewardUI : MonoBehaviour
{
    public static RewardUI instance;

    public GameObject rewardButton;


    public Text currentRewardText;

    public int gamesPlayed;

    // Start is called before the first frame update
    void Start()
    {
        instance = this;
        CloseRewardCanvas();


    }

    // Update is called once per frame
    void Update()
    {

    }


    public void OnClickRewardedVideo()
    {
        AdManager.instance.UserOptToWatchAd();
        CloseRewardCanvas();
    }



  


    public void CloseRewardCanvas()
    {
        this.gameObject.SetActive(false);
    }

    public void ShowReward()
    {
        rewardButton.gameObject.SetActive(true);

        StartCoroutine(ShowCurrentReward(StoreScripts.Score.instance.GetPoint()));
    }





    IEnumerator ShowCurrentReward(int reward)
    {
        int countingReward = 0;
        while (countingReward <= reward)
        {
            currentRewardText.text = countingReward.ToString();
            countingReward += Mathf.Max(1, Mathf.FloorToInt(reward / 7));
            yield return new WaitForSeconds(0.05f);

        }

        currentRewardText.text = reward.ToString();
    }







}
