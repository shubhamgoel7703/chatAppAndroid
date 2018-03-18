package com.example.shubhamgoel.maciot;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by shubhamgoel on 18/03/18.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    public static boolean isToken=false;
    public static String token = "";
    @Override
    public void onTokenRefresh() {
        if(FirebaseInstanceId.getInstance().getToken().length()>2) {
            String recent_token = FirebaseInstanceId.getInstance().getToken();
            Log.d("Token", recent_token);
            token= recent_token;
            isToken=true;
        }
    }
}
