package com.vpnterbaik.premium.utils;

/**
 * Created by appteve on 30/01/2017.
 */

public interface AppData {


    String GOOGLE_BILLING_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgBN9091WFjnx7eH3gWcN4FEmwSK9go5uJW/bzwMy8PMvQ9m6QPtaHdQXJLeLDzrSXEHsPEct71KLuWIHvY5qEy68uL5CTACJx5dOsDg86LFh623TcCSHpPSuswI39TKcBv6XnNgB0j6/9BDoFaHAsTJa7achZdhNFcof86dCtW3IeYtgaJ9ibu43YVkoU3WEq601YeEy56xEAuJBamm4y7Lk+zSsIrrXONWJ8G35zi+H2TdkBEiEe06L4RrxGOeXgfA0/jkPZ0/KbM2bYY02GC73PuqNDvWo4DpgbDH4IfWuAzfBcw3gjfwUKM/w74KdL4VBFX6Nxn1o/jJ+l4iNDwIDAQAB";

    String TEST_PURCHASE_SKU = "android.test.purchased";

    String SUBS_LIFETIME = "com.vpnterbaik.premium.ad"; // lifetime
    String SUBS_1_MONTH = "com.vpnterbaik.premium.monthly"; // monthly
    String SUBS_12_MONTH = "com.vpnterbaik.premium.yearly"; // yearly

    String APP_ID = "ca-app-pub-3076549648990958~6053331325";
    String INTERSTITIAL_ID = "ca-app-pub-3076549648990958/7623780907";
    String BANNER_ID = "ca-app-pub-3076549648990958/1249944245";
    int PAUSE_SHOW_INTERSTITIAL = 30000; // 30 sec
    String MERCHANT_ID = null;

    String APP_PREFERENCES = "UserDataApp";

    String IAP_LIFETIME = "iaplifetime";
    String IAP_MONTHLY = "iapmonthly";
    String IAP_YEARLY = "iapyearly";

    String IS_VIP = "isvip";
    String IS_SUBS = "issub";
    String DATE_PURCHASE = "datepurchase";
    String DATE_END = "dateend";
    String DATE_LIFETIME = "datelifetime";
}
