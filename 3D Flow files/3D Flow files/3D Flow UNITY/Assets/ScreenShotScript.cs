using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;


public class ScreenShotScript : MonoBehaviour {

    // folder to write output (defaults to data path)
    string folder;

    // image #
    int counter = 0; 




    public void setupFolder()
    {

        // if folder not specified by now use a good default
        if (folder == null || folder.Length == 0)
        {
            folder = Application.dataPath;
            if (Application.isEditor)
            {
                // put screenshots in folder above asset path so unity doesn't index the files
                var stringPath = folder + "/..";
                folder = Path.GetFullPath(stringPath);
            }
            folder += "/screenshots";

            // make sure directoroy exists
            System.IO.Directory.CreateDirectory(folder);


        }

        counter = Directory.GetFiles(folder).Length;

        // up counter for next call
        ++counter;


    }





 void Update()
  {




        if (Input.GetKeyDown ("j")) {
            setupFolder();

            string name = string.Format("{3}/{0}x{1}-{2}.png", Screen.width, Screen.height, counter, folder);

            ScreenCapture.CaptureScreenshot(name);

      }

     if (Input.GetKeyDown ("k")) {

         if (Time.timeScale == 0) {

                Time.timeScale = 1;

           }  else {

             Time.timeScale = 0;

           }
      }


        if (Input.GetKeyDown ("l")) {
          StartCoroutine (AdvanceOneFrame ());

      }



   }


    IEnumerator AdvanceOneFrame()
  {
      Time.timeScale = 0.9f;
     yield return null;
     Time.timeScale = 0;
    }








}
