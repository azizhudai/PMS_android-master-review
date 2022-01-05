package com.karatascompany.pys3318.firebase;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by azizmahmud on 3.4.2018.
 */

public class PushNotificationRemote {



    public static void sendPushToSingleInstance(final Context activity, final HashMap dataValue /*your data from the activity*/, final String instanceIdToken /*firebase instance token you will find in documentation that how to get this*/ ) {


        final String url = "https://fcm.googleapis.com/fcm/send"; //https://fcm.googleapis.com/fcm/send
        StringRequest myReq = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity, "Bingo Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Toast.makeText(activity, "Oops error"+error.getMessage()+" localiss"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                  /*  @Override
                    public void onErrorResponse(VolleyError error) {
                        super("");
                        Toast.makeText(activity, "Oops error", Toast.LENGTH_SHORT).show();
                    }*/
                }) {

            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                Map<String,String> rawParameters = new Hashtable<String, String>();
                rawParameters.put("to", instanceIdToken);
                rawParameters.put("notification", new JSONObject(dataValue).toString());

                return new JSONObject(rawParameters).toString().getBytes();
            };

           /* public String getBodyContentType()
            {
                return "application/json";//return "application/json; charset=utf-8";
            }*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key="+"AAAAQj4kfdA:APA91bFafkX6Ad1YKw9jZBnf_xPKZllXcNk7H7LSCOfO8S6pEXXbdq1smFdIZOU9mQfDBOCIDsKASGsRoay76O3joIpKljeFV1L2skLRaTP5tuuTaB5g3MgfCeqdKveVml09M21ggY0T");  //YOUR_LEGACY_SERVER_KEY_FROM_FIREBASE_CONSOLE
                headers.put("content-type","application/json");
                return headers;
            }

        };

        Volley.newRequestQueue(activity).add(myReq);
    }



}
