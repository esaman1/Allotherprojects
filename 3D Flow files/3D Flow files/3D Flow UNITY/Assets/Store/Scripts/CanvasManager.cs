

#pragma warning disable 0618 

using UnityEngine;
using System;
using System.Collections;
using UnityEngine.UI;
using UnityEngine.EventSystems;
//#if AADOTWEEN
using DG.Tweening;
//#endif
#if UNITY_5_3_OR_NEWER
using UnityEngine.SceneManagement;
#endif



namespace StoreScripts
{
	public class CanvasManager : MonoBehaviorHelper 
	{

		public static CanvasManager instance;
	
		[SerializeField] private Text scoreText; 
		[SerializeField] private Text bestScoreText; 
		[SerializeField] private CanvasGroup canvasGroupMaskShop;

		[SerializeField] private Text diamondText;

		[SerializeField] private Button buttonContinueWithLife;
		[SerializeField] private Button buttonContinueWithDiamond;
		[SerializeField] private Button buttonGetFreeLife;
		[SerializeField] private Button buttonRestart;
		[SerializeField] private Button buttonCloseMask;

		

		public delegate void AnimationTransitionInStart();
		public static event AnimationTransitionInStart OnAnimationTransitionInStart;

		public delegate void AnimationTransitionInEnd();
		public static event AnimationTransitionInEnd OnAnimationTransitionInEnd;

		public delegate void AnimationTransitionOutStart();
		public static event AnimationTransitionOutStart OnAnimationTransitionOutStart;

		public delegate void AnimationTransitionOutEnd();
		public static event AnimationTransitionOutEnd OnAnimationTransitionOutEnd;


        public delegate void _SetDiamond(int diamond);
        public static event _SetDiamond OnSetDiamond;



        public void _AnimationTransitionInStart()
		{
			if(OnAnimationTransitionInStart != null)
				OnAnimationTransitionInStart();
		}
		public void _AnimationTransitionInEnd()
		{
			if(OnAnimationTransitionInStart != null)
				OnAnimationTransitionInEnd();
		}
		public void _AnimationTransitionOutStart()
		{
			if(OnAnimationTransitionOutStart != null)
				OnAnimationTransitionOutStart();
		}
		public void _AnimationTransitionOutEnd()
		{
			if(OnAnimationTransitionOutEnd != null)
				OnAnimationTransitionOutEnd();
		}


		float timeAlphaAnim = 1f;

		

		void OnEnable()
		{
			GameManager.OnSetPoint += SetPointText;

			GameManager.OnSetDiamond += SetDiamondText;

			GameManager.OnGameStart += OnGameStart;

			//GameManager.OnGameOverEnded += OnGameOverEnded;
		}

		void OnDisable()
		{
			GameManager.OnSetPoint -= SetPointText;

			GameManager.OnSetDiamond -= SetDiamondText;

			GameManager.OnGameStart -= OnGameStart;

			//GameManager.OnGameOverEnded -= OnGameOverEnded;
		}

		void Start()
		{

            scoreText.gameObject.SetActive(false);
            SetBestScoreText(gameManager.GestBestScore());

			SetPointText(0);

            //lastScoreText.text = "Last: " + gameManager.GestLastScore();







            canvasGroupMaskShop.alpha = 0;

			

			SetDiamondText(gameManager.diamond);

			instance = this;
		}

		void ButtonLogic()
		{
			bool adsInitialized = false;


			bool haveEnoughtDiamond = gameManager.diamond >= 100;

	
			ActivateButton(buttonGetFreeLife, adsInitialized);
			//ActivateButton(buttonGetFreeDiamonds, adsInitialized);
			ActivateButton(buttonContinueWithDiamond, haveEnoughtDiamond);
		}

		void ActivateButton(Button b, bool activate)
		{
			b.GetComponent<CanvasGroup>().alpha = activate ? 1 : 0.5f;
			b.interactable = activate;
		}




		void OnGameStart()
		{
			//		SetCanvasGroupInstructionAlpha(1,0);
			//
			//		canvasGroupInstruction.GetComponent<AnimButtonHierarchy>().DoAnimOut();
		}

		void SetPointText(int point)
		{
			scoreText.text = point.ToString();

			int best = gameManager.GestBestScore ();

            if (point > best)
            {
                SetBestScoreText(point);
                Score.instance.Save();
            }
		}

        public void SetDiamondText(int diamond)
		{
			diamondText.text = diamond.ToString();
		}

		void SetBestScoreText(int p)
		{
			bestScoreText.text = "Best: " + p.ToString();
		}

	
		//public void SetCanvasGroupGameOverAlpha(float fromA, float toA)
		//{
		//	#if AADOTWEEN
		//	DOVirtual.Float(fromA, toA, timeAlphaAnim, (float f) => {
		//		canvasGroupGameOver.alpha = f;
		//	})
		//		.OnComplete(() => {
		//			canvasGroupGameOver.alpha = toA;

		//			if(toA == 0)
		//			{
		//				canvasGroupGameOver.gameObject.SetActive(false);
		//			}

		//			AddButtonListener();
		//		});
		//	#endif
		//}

		void AddButtonListener()
		{
			bool adsInitialized = false;


			bool haveEnoughtDiamond = gameManager.diamond >= 100;


	
			ActivateButton(buttonGetFreeLife, adsInitialized);
			//ActivateButton(buttonGetFreeDiamonds, adsInitialized);
			ActivateButton(buttonContinueWithDiamond, haveEnoughtDiamond);

			buttonRestart.interactable = true;
		}

		void RemoveListener()
		{
			buttonContinueWithLife.interactable = false;
			buttonGetFreeLife.interactable = false;
			//buttonGetFreeDiamonds.interactable = false;
			buttonContinueWithDiamond.interactable = false;
			buttonRestart.interactable = false;
		}

		public void OnClickedButton(GameObject b)
		{
			if(!b.GetComponent<Button>().interactable)
				return;

		 if (b.name.Contains("ButtonMask"))
				OnClickedButtonMask();
		}

		public void OnClickedButtonMask()
		{

        


            canvasGroupMaskShop.gameObject.SetActive(true);

            canvasGroupMaskShop.alpha = 1;

            canvasGroupMaskShop.interactable = true;
			canvasGroupMaskShop.blocksRaycasts = true;

			buttonCloseMask.onClick.AddListener(CloseButtonMask);
		}

		public void CloseButtonMask()
		{
		
					canvasGroupMaskShop.blocksRaycasts = false;
					canvasGroupMaskShop.alpha = 0;
					canvasGroupMaskShop.gameObject.SetActive(false);
	
			canvasGroupMaskShop.interactable = false;
		}





        public int diamond
        {
            get
            {
                int l = PlayerPrefs.GetInt("DIAMOND", 0);

                if (l < 0)
                {
                    l = 0;
                    PlayerPrefs.SetInt("DIAMOND", l);
                    PlayerPrefs.Save();

                    if (OnSetDiamond != null)
                        OnSetDiamond(l);
                }

                return l;
            }

            set
            {
                PlayerPrefs.SetInt("DIAMOND", value);
                PlayerPrefs.Save();

                if (OnSetDiamond != null)
                    OnSetDiamond(value);
            }
        }

        [SerializeField] private Item DIAMOND;

        public int GetDiamond()
        {
            return DIAMOND.GetCount();
        }





        public int AddDiamond(int n)
        {

            int temp = DIAMOND.AddToCount(n);

            if (OnSetDiamond != null)
                OnSetDiamond(temp);

            return temp;
        }






        public void GameOver(){

            Score.instance.Save();

            AdManager.instance.GameOver();

            scoreText.gameObject.SetActive(false);
        }

        public void GameStart()
        {
            scoreText.gameObject.SetActive(true);
        }






        public void StartAddingScoreToCoins()
        {
            StartCoroutine(AnimateCoins());

        }



        IEnumerator AnimateCoins()
        {
            int currentScore = StoreScripts.Score.instance.GetPoint();
            int countingDiamonds = StoreScripts.GameManager.instance.diamond;


            int finalDiamonds = countingDiamonds + currentScore;



            while (countingDiamonds <= finalDiamonds)
            {
                //currentScoreText.text = "Score: " + countingDiamonds;
                //StoreScripts.CanvasManager.instance.
                SetDiamondText(countingDiamonds);
                countingDiamonds += Mathf.Max(1, Mathf.FloorToInt(currentScore / 7));
                yield return new WaitForSeconds(0.05f);
            }
            //currentScoreText.text = "Score: " + score;

            AddScoreToCoins(currentScore);
        }

        public void AddScoreToCoins(int coinAmount)
        {
            //StoreScripts.
            GameManager.instance.diamond += coinAmount;
        }





    }
}
