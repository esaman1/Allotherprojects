# Welcome to Defold

[Build and run](defold://build) to see the game in action.

If you want to modify the game you might want to refer to the Defold documentation for assistance. Check out [the documentation pages](https://defold.com/learn) for examples, tutorials, manuals and API docs.

---

**If you're planning on publishing the iOS version here's a quick run through of what you need to do:**

1. Create your signing credentials for the game in [Apple Developer Center](https://developer.apple.com/account) and setup a new app in [App Store Connect](https://appstoreconnect.apple.com).  
    - You need to create a distribution certificate, app ID and provisioning profile.
    - When you setup the app in App Store Connect make sure to select the same bundle identifier (app ID) you created in the [Apple Developer Center](https://developer.apple.com/account). You can read more about the signing process here: [iOS development](https://www.defold.com/manuals/ios/)

2. In the Defold project, open game.project and scroll down to the iOS section. Look for "Bundle Identifier" and add the bundle identifier you created in the [Apple Developer Center](https://developer.apple.com/account).

3. Open main/data.lua and add your app store developer url, and the app store url for the game. These are used for some of the buttons on the game over screen.
    - You can get your developer url from [Apple Developer Center](https://developer.apple.com/account).
    - For the app store url you just need to get the "Apple ID" from the App Information section in [App Store Connect](https://appstoreconnect.apple.com) and paste it at the end of the placeholder url in main/data.lua.

4. Open main/admob.script and add your Admob app id, banner id, interstitial id and rewarded id for iOS.
    - You get these id's from [Admob](https://admob.google.com) by setting up an app for iOS just like you did for Android.

5. Once you've set up your in-app purchase (for removing the ads) in [App Store Connect](https://appstoreconnect.apple.com), open main/iap.script and add the product id for iOS.

6. Make sure all files are saved, then go to Project > Bundle > iOS Application. Add your signing credentials, select "release" as the variant and click create bundle.

7. Now you can upload the ipa file to [App Store Connect](https://appstoreconnect.apple.com) using application loader.
    - If you have Xcode installed you should be able to search for application loader in your applications folder.

8. Once the ipa file is uploaded to [App Store Connect](https://appstoreconnect.apple.com), make sure all the information is filled out, upload the app icon and screenshots and submit for review.
    - You can find the app icon and screenshots in the main folder that contains all of the project files and assets.
    - If needed you can create more screenshots for different devices by installing and running the game on the Xcode simulators.

---

Note: Some functionality like in-app purchases and Admob only work on iOS and Android devices. To test on a real device follow these Defold guides:  

[Mobile development app](https://www.defold.com/manuals/dev-app/)  
[iOS development](https://www.defold.com/manuals/ios/)   
[Android development](https://www.defold.com/manuals/android/)

If you run into trouble with Defold, help is available in [the forum](https://forum.defold.com).

---
