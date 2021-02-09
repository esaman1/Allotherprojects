


using UnityEngine;
using System.Collections;


namespace StoreScripts
{
	public class Score 
	{
		public static Score instance;

	

		private int point;
		private int last;
		private int best;
		private bool lastIsBest;

		public Score()
		{
			instance = this;
			
			this.point = 0;
			this.last = PlayerPrefs.GetInt("SCORE",0);
			this.best = PlayerPrefs.GetInt("SCORE_BEST",0);
			this.lastIsBest = false;
		}

		//return current score
		public int AddPoint(int p)
		{
			this.point += p;
			return GetPoint();
		}

		public int SetPoint(int p)
		{
			this.point = p;
			return this.point;
		}

		public int GetPoint()
		{
			return this.point;
		}
		//return true if best
		public bool Save()
		{
			PlayerPrefs.SetInt("SCORE",this.point);

			if(this.best < this.point)
			{
				this.lastIsBest = true;

				PlayerPrefs.SetInt("SCORE_BEST",this.point);

				return true;
			}

			int tempbest = PlayerPrefs.GetInt("SCORE_BEST",0);
			


			this.lastIsBest = false;

			return false;
		}

		public int GetLast()
		{
			return this.last;
		}

		public int GetBest()
		{
			return this.best;
		}

		public bool GetLastIsBest()
		{
			return this.lastIsBest;
		}
	}
}