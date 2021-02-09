using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestButtons : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }






    public void OnClickGiveCoins (){
        StoreScripts.CanvasManager.instance.StartAddingScoreToCoins();

    }


    public void OnClickAddPoints()
    {
        StoreScripts.GameManager.instance.AddPoint(10);


    }

    public void OnClickGameOver()
    {
        StoreScripts.CanvasManager.instance.GameOver();

    }

    public void ResetScene(){


    }


}
