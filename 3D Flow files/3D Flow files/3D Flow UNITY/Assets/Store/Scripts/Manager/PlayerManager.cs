using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;

using UnityEngine.Advertisements;



#if AADOTWEEN
using DG.Tweening;
#endif



namespace StoreScripts
{
    public class PlayerManager : MonoBehaviorHelper


    {

        public static PlayerManager instance;


        float counter;
        int c = 0;
        public Renderer playerRenderer;

        public Material playerMaterial;

        //public SpriteRenderer mask;

        //public List<Material> listMaterial;
        public List<Color> listColor;

        //public List<Sprite> listMask;
        public void SetMask(Color s)
        {
            if (s == null)
            {
                print("mask 0");
                PlayerPrefs.SetInt("MASK", 0);
                PlayerPrefs.Save();
                return;
            }

            string colorHex = ColorUtility.ToHtmlStringRGB(s);

            for (int i = 0; i < listColor.Count; i++)
            {
                string listHex = ColorUtility.ToHtmlStringRGB(listColor[i]);

                if (string.Equals(colorHex, listHex))
                {
                    print("mask " + i);
                    PlayerPrefs.SetInt("MASK", i);
                    PlayerPrefs.Save();
                    return;
                }
            }
            ChangePlayerMask();





        }






        public void ChangePlayerMask()
        {
            // Sprite s = null;
            int num = PlayerPrefs.GetInt("MASK", 0);
            // s = listMask[num];
            // mask.sprite = s;
            // mask.gameObject.SetActive(s != null);




            //playerRenderer.material = listMaterial[num];

            playerMaterial.color = listColor[num];

        }





        void OnAnimationTransitionOutStart()
        {
            // if(mask.gameObject.activeInHierarchy)
            // {
            // 	mask.transform.localScale = Vector2.one * 30;
            // 	#if AADOTWEEN
            // 	mask.transform.DOScale(1.2f * Vector2.one,1);
            // 	mask.transform.DOLocalRotate(-Vector3.forward * 360,1,RotateMode.FastBeyond360);
            // 	#endif
            // }

            CanvasManager.OnAnimationTransitionOutStart -= OnAnimationTransitionOutStart;
        }



        void Awake()
        {
            listColor = new List<Color>();

            var maskIcons = canvasManager.gameObject.GetComponentsInChildren<MaskIcon>(true);

            foreach (var m in maskIcons)
            {
                //Sprite s = null;
                var i = m.spriteMask.color;
                // if (i != null)
                // {
                //     s = i.sprite;
                // }
                listColor.Add(i);
            }

            ChangePlayerMask();


        }










        // void change()
        // {
        //     c++;
        //     if (c == 7)
        //     {
        //         c = 0;
        //     }

        //     playerRenderer.material = listMaterial[c];


        // }





        void Start()
        {

            instance = this;
            counter = 1;
        }



        // void Update(){
        // 	counter -= 0.01f;
        // 	if (counter <0){
        // 		counter = 0.02f;
        // 		change();
        // 	}
        // }






    }
}