package com.example.bigboss.bigboss;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {

        SharePreferenceUtils.getInstance().saveString("token" , s);

        Log.d("toekn" , s);



        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("message" , remoteMessage.getNotification().getBody());

        super.onMessageReceived(remoteMessage);
    }
}
