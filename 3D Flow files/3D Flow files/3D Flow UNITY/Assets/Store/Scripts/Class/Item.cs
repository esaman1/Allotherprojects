


using UnityEngine;
using System;
using System.Collections;
using UnityEngine.Events;


namespace StoreScripts
{
	public class Item 
	{
		private int count;

		private ItemType itemType;

		public Item(ItemType itemType, int defaultSize)
		{
			this.itemType = itemType;
			this.count = PlayerPrefs.GetInt(this.itemType.ToString(),defaultSize);
		}

		public int GetCount()
		{
			return this.count;
		}

		public int SetCount(int c)
		{
			this.count = c;
			PlayerPrefs.SetInt(this.itemType.ToString(),this.count);
			return this.count;
		}

		public int AddToCount(int c)
		{
			this.count += c;
			PlayerPrefs.SetInt(this.itemType.ToString(),this.count);
			return this.count;
		}

		public ItemType GetItemType()
		{
			return this.itemType;
		}
	}
}