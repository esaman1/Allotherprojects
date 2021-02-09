
using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;



namespace StoreScripts
{
	public class GameManager : MonoBehaviorHelper 
	{
        public static GameManager instance;

        public delegate void _GameStart();
		public static event _GameStart OnGameStart;

		public delegate void _GameOverStart();
		public static event _GameOverStart OnGameOverStarted;

		public delegate void _GameOverEnd();
		public static event _GameOverEnd OnGameOverEnded;

		public delegate void _SetPoint(int point);
		public static event _SetPoint OnSetPoint;

		public delegate void _SetDiamond(int diamond);
		public static event _SetDiamond OnSetDiamond;




		

		//obstacle prefabs

		[SerializeField] private Score SCORE;
		[SerializeField] private Item DIAMOND;


		public int AddPoint(int p)
		{
			int temp = this.SCORE.AddPoint(p);

			if(OnSetPoint != null)
				OnSetPoint(temp);

			return temp;
		}

		public int GestBestScore()
		{
			return this.SCORE.GetBest();
		}

		public int GestLastScore()
		{
			return this.SCORE.GetLast();
		}

		public int GetPoint()
		{
			return this.SCORE.GetPoint();
		}

		

		public int GetDiamond()
		{
			return DIAMOND.GetCount();
		}

		public int AddDiamond(int n)
		{

			int temp = DIAMOND.AddToCount(n);

			if(OnSetDiamond!=null)
				OnSetDiamond(temp);

			return temp;
		}



		public int diamond
		{
			get
			{
				int l = PlayerPrefs.GetInt("DIAMOND",0);

				if(l <0)
				{
					l = 0;
					PlayerPrefs.SetInt("DIAMOND",l);
					PlayerPrefs.Save();

					if(OnSetDiamond!=null)
						OnSetDiamond(l);
				}

				return l;
			}

			set
			{
				PlayerPrefs.SetInt("DIAMOND",value);
				PlayerPrefs.Save();

				if(OnSetDiamond!=null)
					OnSetDiamond(value);
			}
		}

		private void Awake()
		{
            instance = this;


            this.SCORE = new Score();
			this.DIAMOND = new Item(ItemType.DIAMOND,0);

			Application.targetFrameRate = 60;

		


		
			//
			//		DoSpawnParallax();
		}


	

		IEnumerator Start()
		{
			
			GC.Collect ();

			yield return 0;
		}

	



		public void OnStart()
		{
			

			SCORE.SetPoint(0);
		


			if(OnGameStart != null)
				OnGameStart ();


			if(OnSetPoint != null)
				OnSetPoint(0);

			//countSpawn = 0;


		}


		//[NonSerialized] public float smallSpace = 3f;
		//public Vector2 lastPos ;
		//int countSpawn = 0;
		//int diamondSpawnCount = 0;


		//bool lastIsBig = false;

		//int positionDisplayedBest = -1;
		//int positionDisplayedLast = -1;

	

	

	

		void OnApplicationQuit()
		{
			PlayerPrefs.Save();
		}

		
	}
}