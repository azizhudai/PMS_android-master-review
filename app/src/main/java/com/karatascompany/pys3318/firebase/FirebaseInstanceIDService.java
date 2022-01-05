package com.karatascompany.pys3318.firebase;

import android.os.StrictMode;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by azizmahmud on 2.4.2018.
 */

public class FirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

  /*  @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);

        sendRegistrationToServer(token);
    }*/

    private void sendRegistrationToServer(String token) {

        // token'ı servise gönderme işlemlerini bu methodda yapmalısınız
    }

    public static void sendAndroidNotification(String deviceToken, String message, String title) throws IOException {

        final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
        final String CONTENT_TYPE = "application/json";
        final String AUTH_KEY_FCM = "AAAAQj4kfdA:APA91bFafkX6Ad1YKw9jZBnf_xPKZllXcNk7H7LSCOfO8S6pEXXbdq1smFdIZOU9mQfDBOCIDsKASGsRoay76O3joIpKljeFV1L2skLRaTP5tuuTaB5g3MgfCeqdKveVml09M21ggY0T";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        try {
            msgObject.put("body", message);
            Log.d("Body",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            msgObject.put("title", title);
            Log.d("Title",title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* try {
            msgObject.put("icon", R.mipmap.ic_launcher );

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //  msgObject.put("color", );

        try {
            obj.put("to", deviceToken);
            Log.d("to",deviceToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("notification",msgObject);
            Log.d("notification",msgObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, obj.toString());
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(API_URL_FCM).post(body)
                .addHeader("content-type", CONTENT_TYPE)
                .addHeader("Authorization", "key="+AUTH_KEY_FCM).build();
        Log.d("Request",request.toString());
        //  Response response = client.newCall(request).execute();
        //     Log.d("Notification response",response.body().toString());

    }


    // Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "API_KEY_HERE";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";


}
